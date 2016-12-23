package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.messages.GetMessages;

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
        export("messages_get", new GetMessages());
        validators = new HashMap<>();
    }
    
    private final void buildPanels(PageBuilderContext context) {
        Object[] panel;
        StandardPanel panels;
        
        panels = new StandardPanel(context);
        for (String key : context.panels.keySet()) {
            panel = context.panels.get(key);
            panels.instance(key,
                    (AbstractPanelPage)panel[0], (ExtendedContext)panel[1]);
        }
    }
    
    public abstract void config(PageBuilderContext context) throws Exception;
    
    public void config(GetFieldsProperties config) { };
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractContext> T configOnly() {
        context = new PageBuilderContext();
        context.function = this;
        return (T)context;
    }
    
    protected final void description(String name, String model, String field) {
        validate(name, new DescriptionValidate(model, field));
    }
    
    protected final PageBuilderContext getContext() {
        return context;
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
        Object[] objects;
        
        context = configOnly();
        context.view = view;
        config(context);
        
        if (context.panels.size() > 0)
            buildPanels(context);
        
        if (context.messages != null)
            context.messages.entries();
        
        customaction = new BuilderCustomAction();
        reassignCustomActions(context);
        
        message = new Message("nc_data_get");
        message.add("style_constants", view.getStyleConstants());
        service = new GenericService(context.function, SERVICE);
        objects = service.invoke(message);
        
        context.ncsheet = (Object[][])objects[0];
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
        ViewSpec viewspec;
        ViewConfig viewconfig;
        ViewInput viewinput;
        
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
    public void validate() {
        InputComponent input = getInput();
        input.setText(getText(input.get()));
    }
}