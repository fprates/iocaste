package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.NavControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.TableTool;
import org.iocaste.shell.common.TableToolData;
import org.iocaste.shell.common.View;

public abstract class AbstractPageBuilder extends AbstractPage {
    private PageBuilderContext context;
    private StandardInstallContext installcontext;
    private PageBuilderDefaultInstall defaultinstall;
    
    public AbstractPageBuilder() {
        export("install", "install");
    }
    
    public abstract PageBuilderContext config();
    
    @SuppressWarnings("unchecked")
    protected final <T extends AbstractInstallObject> T getDefaultInstallObject(
            ) {
        return (T)defaultinstall;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *    org.iocaste.shell.common.View)
     */
    @Override
    public AbstractContext init(View view) {
        BuilderCustomView customview;
        Map<String, AbstractViewSpec> viewspecs;
        AbstractViewSpec viewspec;
        ViewConfig viewconfig;
        AbstractViewInput viewinput;
        
        context = config();
        context.view = view;
        context.function = this;

        viewspecs = context.getViewSpecs();
        for (String name : viewspecs.keySet()) {
            viewspec = viewspecs.get(name);
            viewconfig = context.getViewConfig(name);
            viewinput = context.getViewInput(name);
            
            context.addViewComponents(name);
            customview = new BuilderCustomView();
            customview.setViewSpec(viewspec);
            customview.setViewConfig(viewconfig);
            customview.setViewInput(viewinput);
            customview.setName(name);
            ((AbstractPage)context.function).register(name, customview);
        }
        
        return context;
    }
    
    public final InstallData install(Message message) {
        Map<String, AbstractInstallObject> objects;
        InstallData data;
        String pkgname = message.getString("name");
        String pkgnameuc = pkgname.toUpperCase();
        
        installcontext = new StandardInstallContext();
        defaultinstall = new PageBuilderDefaultInstall(pkgname);
        defaultinstall.setLink(pkgnameuc, pkgname);
        defaultinstall.setProfile(pkgnameuc);
        defaultinstall.setTaskGroup(pkgnameuc);
        installConfig();
        objects = installcontext.getObjects();
        objects.put("default", defaultinstall);
        data = installcontext.getInstallData();
        for (String name : objects.keySet())
            objects.get(name).run(installcontext);
        
        return data;
    }
    
    protected void installConfig() {
        installObject("default", defaultinstall);
    }
    
    protected final void installObject(
            String name, AbstractInstallObject object) {
        installcontext.put(name, object);
    }
}

class BuilderCustomView extends AbstractCustomView {
    private NavControl navcontrol;
    private String name;
    
    private void buildItem(PageBuilderContext context, ViewSpecItem item) {
        TableToolData ttdata;
        ViewComponents viewcomponents;
        Container container = context.view.getElement(item.getParent());
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        String name = item.getName();
        
        switch (types[item.getType()]) {
        case FORM:
            new Form(context.view, name);
            break;
        case PAGE_CONTROL:
            navcontrol = new NavControl((Form)container, context);
            break;
        case STANDARD_CONTAINER:
            new StandardContainer(container, name);
            break;
        case TABBED_PANE:
            new TabbedPane(container, name);
            break;
        case TABBED_PANE_ITEM:
            new TabbedPaneItem((TabbedPane)container, name);
            break;
        case TABLE_TOOL:
            ttdata = new TableToolData();
            ttdata.container = container;
            ttdata.context = context;
            ttdata.name = name;
            
            viewcomponents = context.getViewComponents(this.name);
            viewcomponents.tabletools.put(ttdata.name, new TableTool(ttdata));
            break;
        case DATA_FORM:
            new DataForm(container, name);
            break;
        case TEXT_EDITOR:
            
        }
        
        for (ViewSpecItem child : item.getItems())
            buildItem(context, child);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.CustomView#execute(
     *    org.iocaste.shell.common.AbstractContext)
     */
    @Override
    public void execute(AbstractContext context) {
        AbstractViewSpec viewspec = getViewSpec();
        ViewConfig viewconfig = getViewConfig();
        AbstractViewInput viewinput = getViewInput();
        PageBuilderContext _context = (PageBuilderContext)context;
        
        viewspec.execute();
        for (ViewSpecItem item : viewspec.getItems())
            buildItem((PageBuilderContext)context, item);
        
        viewconfig.setNavControl(navcontrol);
        viewconfig.run(_context);
        
        viewinput.run(_context);
    }
    
    public final void setName(String name) {
        this.name = name;
    }
}
