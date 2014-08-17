package org.iocaste.appbuilder.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
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
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.RadioGroup;
import org.iocaste.shell.common.ReportTool;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewCustomAction;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public abstract class AbstractPageBuilder extends AbstractPage {
    private PageBuilderContext context;
    private StandardInstallContext installcontext;
    private PageBuilderDefaultInstall defaultinstall;
    
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
        BuilderCustomView customview;
        AbstractViewSpec viewspec;
        ViewConfig viewconfig;
        AbstractViewInput viewinput;
        AbstractActionHandler handler;
        BuilderCustomAction customaction;
        
        context = new PageBuilderContext();
        context.view = view;
        context.function = this;
        config(context);

        customaction = new BuilderCustomAction();
        for (String name : context.getViews()) {
            viewspec = context.getViewSpec(name);
            viewconfig = context.getViewConfig(name);
            viewinput = context.getViewInput(name);
            
            customview = new BuilderCustomView();
            customview.setViewSpec(viewspec);
            customview.setViewConfig(viewconfig);
            customview.setViewInput(viewinput);
            customview.setView(name);
            
            register(name, customview);
            for (String action : context.getActions(name)) {
                handler = context.getActionHandler(name, action);
                customaction.addHandler(name, action, handler);
                register(action, customaction);
            }
        }
        
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
}

class BuilderCustomView extends AbstractCustomView {
    private TextEditor editor;
    private NavControl navcontrol;
    private String view;
    
    private void buildItem(PageBuilderContext context, ViewSpecItem item) {
        RadioGroup group;
        String[] names;
        DashboardFactory dashboard;
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
            ttdata = new TableToolData(container, name);
            viewcomponents = context.getViewComponents(view);
            viewcomponents.add(ttdata);
            break;
        case DATA_FORM:
            new DataForm(container, name);
            break;
        case DASHBOARD:
            dashboard = new DashboardFactory(container, context, name);
            viewcomponents = context.getViewComponents(view);
            viewcomponents.dashboards.put(name, dashboard);
            break;
        case DASHBOARD_ITEM:
            viewcomponents = context.getViewComponents(view);
            dashboard = viewcomponents.dashboards.get(item.getParent());
            dashboard.instance(name);
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
        case REPORT_TOOL:
            viewcomponents = context.getViewComponents(view);
            viewcomponents.reporttools.put(
                    name, new ReportTool(container, name));
            break;
        case TEXT_EDITOR:
            editor = new TextEditorTool(context).instance(container, name);
            viewcomponents = context.getViewComponents(view);
            viewcomponents.editors.put(name, editor);
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
        default:
            break;
        }
        
        for (ViewSpecItem child : item.getItems())
            buildItem(context, child);
    }
    
    private final void download(PageBuilderContext context) throws Exception {
        File file = new File(context.downloaddata.fullname);
        FileInputStream fis = new FileInputStream(file);
        byte[] content = new byte[fis.available()];
        
        fis.read(content);
        fis.close();
        
        context.view.setContentType("application/octet-stream");
        context.view.setHeader("Content-Disposition",
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
        AbstractViewSpec viewspec = getViewSpec();
        ViewConfig viewconfig = getViewConfig();
        AbstractViewInput viewinput = getViewInput();
        PageBuilderContext _context = (PageBuilderContext)context;
        
        if (_context.downloaddata != null) {
            download(_context);
            _context.downloaddata = null;
            return;
        }
        
        if (!context.view.keepView() || !viewspec.isInitialized()) {
            _context.setInputUpdate(false);
            viewspec.run(_context);
            for (ViewSpecItem item : viewspec.getItems())
                buildItem((PageBuilderContext)context, item);
            
            if (viewconfig != null) {
                viewconfig.setNavControl(navcontrol);
                viewconfig.run(_context);
            }
            
            viewspec.setInitialized(true);
            if (viewinput != null) {
                viewinput.run(_context);
                generateTables(_context);
            }
            return;
        }
        
        if (_context.isInputUpdatable() && viewinput != null) {
            _context.setInputUpdate(false);
            viewinput.run(_context);
            updateTables(_context);
        }
    }
    
    private final void generateTables(PageBuilderContext context) {
        ViewComponents components = context.
                getViewComponents(context.view.getPageName());
        
        for (TableToolEntry entry : components.tabletools.values())
            entry.component = new TableTool(context, entry.data);
    }
    
    public final void setView(String view) {
        this.view = view;
    }
    
    private final void updateTables(PageBuilderContext context) {
        ViewComponents components = context.
                getViewComponents(context.view.getPageName());
        
        for (TableToolEntry entry : components.tabletools.values())
            entry.component.setObjects(entry.data.objects);
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
    public void execute(AbstractContext context) throws Exception {
        String view = context.view.getPageName();
        String action = context.view.getActionControl();
        AbstractActionHandler handler = handlers.get(view).get(action);
        
        if (handler == null)
            throw new RuntimeException(action.concat(" isn't a valid action."));
        
        handler.run(context);
        
    }
}