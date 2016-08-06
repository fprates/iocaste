package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Media;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.View;

public abstract class AbstractPageBuilder extends AbstractPage {
    private static final String SERVICE = "/iocaste-appbuilder/services.html";
    private PageBuilderContext context;
    private StandardInstallContext installcontext;
    private PageBuilderDefaultInstall defaultinstall;
    private BuilderCustomAction customaction;
    private Map<String, AbstractExtendedValidator> validators;
    
    public AbstractPageBuilder() {
        export("install", "install");
        export("fields_properties_get", new GetFieldsProperties());
        validators = new HashMap<>();
    }
    
    public abstract void config(PageBuilderContext context) throws Exception;
    
    public void config(GetFieldsProperties config) { };
    
    protected final void description(String name, String model, String field) {
        validate(name, new DescriptionValidate(model, field));
    }
    
    @SuppressWarnings("unchecked")
    private final void extend(Object[] objects, StyleSheet stylesheet) {
        Map<String, Map<String, String>> sheet;
        String mediakey;
        Media media;
        Object[][] appbuildersheet = (Object[][])objects[0];
        
        for (int i = 0; i < appbuildersheet.length; i++) {
            mediakey = (String)appbuildersheet[i][0];
            media = stylesheet.instanceMedia(mediakey);
            media.setRule((String)appbuildersheet[i][1]);
            sheet = (Map<String, Map<String, String>>)appbuildersheet[i][2];
            stylesheet.add(mediakey, sheet);
        }
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *    org.iocaste.shell.common.View)
     */
    @Override
    public AbstractContext init(View view) throws Exception {
        GenericService service;
        Message message;
        AbstractExtendedValidator validator;
        Object[] objects;
        Documents documents = null;
        
        context = new PageBuilderContext();
        context.view = view;
        context.function = this;
        config(context);
        for (String key : validators.keySet()) {
            if (documents == null)
                documents = new Documents(context.function);
            validator = validators.get(key);
            validator.setDocuments(documents);
            register(key, validator);
        }
        customaction = new BuilderCustomAction();
        reassignCustomActions(context);
        
        message = new Message("nc_data_get");
        message.add("style_constants", view.getStyleConstants());
        service = new GenericService(context.function, SERVICE);
        objects = service.invoke(message);
        
        context.stylesheet = StyleSheet.instance(context.view);
        extend(objects, context.stylesheet);
        context.ncspec = (Object[][])objects[1];
        context.ncconfig = (Object[][])objects[2];
        return context;
    }
    
    public final InstallData install(Message message) throws Exception {
        Map<String, AbstractInstallObject> objects;
        InstallData data;
        String pkgname = message.getst("name");
        
        installcontext = new StandardInstallContext();
        defaultinstall = new PageBuilderDefaultInstall(pkgname);
        installObject("default", defaultinstall);
        installConfig(defaultinstall);
        objects = installcontext.getObjects();
        objects.put("default", defaultinstall);
        data = installcontext.getInstallData();
        for (String name : objects.keySet())
            objects.get(name).run(installcontext);
        
        return data;
    }
    
    protected abstract void installConfig(
            PageBuilderDefaultInstall defaultinstall) throws Exception;
    
    protected final void installObject(
            String name, AbstractInstallObject object) {
        installcontext.put(name, object);
    }
    
    private final void reassignCustomActions(PageBuilderContext context) {
        ViewContext viewctx;
        BuilderCustomView customview;
        AbstractViewSpec viewspec;
        ViewConfig viewconfig;
        AbstractViewInput viewinput;
        
        for (String name : context.getViews()) {
            viewctx = context.getView(name);
            viewspec = viewctx.getSpec();
            viewconfig = viewctx.getConfig();
            viewinput = viewctx.getInput();
            
            customview = new BuilderCustomView();
            customview.setViewSpec(viewspec);
            customview.setViewConfig(viewconfig);
            customview.setViewInput(viewinput);
            customview.setView(name);
            
            register(name, customview);
            for (String action : viewctx.getActions())
                register(name, action, viewctx);
        }
    }
    
    public final void register(String view, String action, ViewContext viewctx)
    {
        AbstractActionHandler handler;
        handler = viewctx.getActionHandler(action);
        customaction.addHandler(view, action, handler);
        register(action, customaction);
    }
    
    protected final void validate(
            String name, AbstractExtendedValidator validator) {
        validators.put(name, validator);
    }
}

class DescriptionValidate extends AbstractExtendedValidator {
    
    public DescriptionValidate(String model, String field) {
        setTextReference(model, field);
    }

    @Override
    protected void validate() {
        InputComponent input = getInput();
        input.setText(getText(input.get()));
    }
}