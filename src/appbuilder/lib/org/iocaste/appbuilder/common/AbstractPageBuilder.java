package org.iocaste.appbuilder.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.tabletool.SetObjects;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.ExpandBar;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.PrintArea;
import org.iocaste.shell.common.RadioGroup;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewCustomAction;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public abstract class AbstractPageBuilder extends AbstractPage {
    private PageBuilderContext context;
    private StandardInstallContext installcontext;
    private PageBuilderDefaultInstall defaultinstall;
    private BuilderCustomAction customaction;
    
    public AbstractPageBuilder() {
        export("install", "install");
    }
    
    public abstract void config(PageBuilderContext context) throws Exception;
    
    protected byte[] getApplicationContext(String installctx) throws Exception {
        byte[] buffer;
        int size;
        InputStream is;
        File file = new File(installctx);
        
        size = ((Number)file.length()).intValue();
        buffer = new byte[size];
        is = new FileInputStream(file);
        is.read(buffer);
        is.close();
        
        return buffer;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *    org.iocaste.shell.common.View)
     */
    @Override
    public AbstractContext init(View view) throws Exception {
        
        context = new PageBuilderContext();
        context.view = view;
        context.function = this;
        config(context);
        
        customaction = new BuilderCustomAction();
        reassignCustomActions(context);
        
        return context;
    }
    
    public final InstallData install(Message message) throws Exception {
        Map<String, AbstractInstallObject> objects;
        InstallData data;
        String pkgname = message.getString("name");
        
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
    
    protected final void reassignCustomActions(PageBuilderContext context) {
        ViewContext viewctx;
        BuilderCustomView customview;
        AbstractViewSpec viewspec;
        ViewConfig viewconfig;
        AbstractViewInput viewinput;
        AbstractActionHandler handler;
        
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
            for (String action : viewctx.getActions()) {
                handler = viewctx.getActionHandler(action);
                customaction.addHandler(name, action, handler);
                register(action, customaction);
            }
        }
        
    }
}

class BuilderCustomView extends AbstractCustomView {
    private TextEditor editor;
    private NavControl navcontrol;
    private String view;
    
    private void buildItem(PageBuilderContext context,
            ViewComponents components, ViewSpecItem item) {
        RadioGroup group;
        String[] names;
        DashboardFactory dashboard;
        DashboardComponent dashboardgroup;
        TableToolData ttdata;
        ReportToolData rtdata;
        String parent = item.getParent();
        Container container;
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        String name = item.getName();

        container = context.view.getElement(parent);
        
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
            ttdata.context = context;
            ttdata.name = name;
            new StandardContainer(container, ttdata.name);
            components.add(ttdata);
            break;
        case DATA_FORM:
            new DataForm(container, name);
            break;
        case DASHBOARD:
            dashboard = new DashboardFactory(container, context, name);
            components.dashboards.put(name, dashboard);
            break;
        case DASHBOARD_GROUP:
            dashboard = components.dashboards.get(parent);
            dashboardgroup = dashboard.instance(name);
            components.dashboardgroups.put(name, dashboardgroup);
            break;
        case DASHBOARD_ITEM:
            dashboard = components.dashboards.get(parent);
            if (dashboard == null) {
                dashboardgroup = components.dashboardgroups.get(parent);
                dashboardgroup.instance(name);
            } else {
                dashboard.instance(name);
            }
            break;
        case EXPAND_BAR:
            new ExpandBar(container, name);
            break;
        case NODE_LIST:
            new NodeList(container, name);
            break;
        case TEXT:
            new Text(container, name);
            break;
        case LINK:
            new Link(container, name, name);
            break;
        case LISTBOX:
            new ListBox(container, name);
            break;
        case REPORT_TOOL:
            rtdata = new ReportToolData();
            rtdata.context = context;
            rtdata.name = name;
            new StandardContainer(container, rtdata.name);
            components.add(rtdata);
            break;
        case TEXT_EDITOR:
            editor = new TextEditorTool(context).instance(container, name);
            components.editors.put(name, editor);
            break;
        case TEXT_FIELD:
            new TextField(container, name);
            break;
        case FILE_UPLOAD:
            new FileEntry(container, name);
            break;
        case BUTTON:
            new Button(container, name);
            break;
        case RADIO_GROUP:
            new RadioGroup(container, name);
            break;
        case RADIO_BUTTON:
            names = name.split("\\.", 2);
            group = context.view.getElement(names[0]);
            group.button(container, names[1]);
            break;
        case SKIP:
            new StandardContainer(container, name).setStyleClass("skip");
            break;
        case PRINT_AREA:
            new PrintArea(container, name);
            break;
        case PARAMETER:
            new Parameter(container, name);
            break;
        default:
            break;
        }
        
        for (ViewSpecItem child : item.getItems())
            buildItem(context, components, child);
    }
    
    private final void download(PageBuilderContext context) throws Exception {
        File file = new File(context.downloaddata.fullname);
        FileInputStream fis = new FileInputStream(file);
        byte[] content = new byte[fis.available()];
        
        fis.read(content);
        fis.close();
        
        context.function.setContentType("application/octet-stream");
        context.function.setHeader("Content-Disposition",
                new StringBuilder("attachment; filename=\"").
                append(context.downloaddata.filename).
                append("\"").toString());
        context.view.setContent(content);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.CustomView#execute(
     *    org.iocaste.shell.common.AbstractContext)
     */
    @Override
    public void execute(AbstractContext context) throws Exception {
        ViewContext viewctx;
        AbstractViewSpec viewspec = getViewSpec();
        ViewConfig viewconfig = getViewConfig();
        AbstractViewInput viewinput = getViewInput();
        PageBuilderContext _context = (PageBuilderContext)context;
        
        if (_context.downloaddata != null) {
            download(_context);
            _context.downloaddata = null;
            return;
        }
        
        if (!viewspec.isInitialized()) {
            _context.setInputUpdate(false);
            _context.view.clear();
            viewspec.run(_context);
            viewctx = _context.getView(view);
            for (ViewSpecItem item : viewspec.getItems())
                buildItem(_context, viewctx.getComponents(), item);
            
            if (viewconfig != null) {
                viewconfig.setNavControl(navcontrol);
                viewconfig.run(_context);
            }

            if (navcontrol != null)
                navcontrol.build(_context);
            
            viewspec.setInitialized(!viewctx.isUpdatable());
            if (viewinput != null) {
                viewinput.run(_context, true);
                generateComponents(_context);
            }
            return;
        }
        
        if (_context.isInputUpdatable() && viewinput != null) {
            _context.setInputUpdate(false);
            viewinput.run(_context, false);
            updateTables(_context);
        }
    }
    
    private final void generateComponents(PageBuilderContext context) {
        ViewComponents components = context.
                getView(context.view.getPageName()).getComponents();
        
        for (DashboardFactory factory : components.dashboards.values())
            factory.build();
            
        for (TableToolEntry entry : components.tabletools.values())
            entry.component = new TableTool(context, entry.data.name);
        
        for (ReportToolEntry entry : components.reporttools.values())
            entry.component = new ReportTool(entry.data);
    }
    
    public final void setView(String view) {
        this.view = view;
    }
    
    private final void updateTables(PageBuilderContext context) {
        ViewComponents components;
        
        components = context.getView(context.view.getPageName()).
                getComponents();
        if (components.tabletools.size() == 0)
            return;
        
        for (TableToolEntry entry : components.tabletools.values()) {
            if (!entry.update)
                continue;
            
            SetObjects.run(entry.component, entry.data);
        }
    }
}

class BuilderCustomAction implements ViewCustomAction {
    private static final long serialVersionUID = 2367760748660650540L;
    private Map<String, Map<String, AbstractActionHandler>> handlers;
    
    public BuilderCustomAction() {
        handlers = new HashMap<>();
    }
    
    public final void addHandler(String view,
            String action, AbstractActionHandler handler) {
        Map<String, AbstractActionHandler> actions = handlers.get(view);
        
        if (actions == null) {
            actions = new HashMap<>();
            handlers.put(view, actions);
        }
        
        actions.put(action, handler);
    }

    @Override
    public final void execute(AbstractContext context) throws Exception {
        String view = context.view.getPageName();
        AbstractActionHandler handler = handlers.get(view).
                get(context.action);
        
        if (handler == null)
            throw new RuntimeException(
                    context.action.concat(" isn't a valid action."));
        
        loadTableObjects((PageBuilderContext)context);
        handler.run(context);
    }
    
    private final void loadTableObjects(PageBuilderContext context) {
        ViewComponents components = context.
                getView(context.view.getPageName()).getComponents();
        
        for (TableToolEntry entry : components.tabletools.values())
            entry.component.refresh(entry.data);
    }
}