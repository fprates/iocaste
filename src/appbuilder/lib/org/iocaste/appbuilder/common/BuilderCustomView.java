package org.iocaste.appbuilder.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.factories.SpecFactory;
import org.iocaste.appbuilder.common.factories.StandardContainerFactory;
import org.iocaste.appbuilder.common.factories.TabbedPaneFactory;
import org.iocaste.appbuilder.common.factories.TabbedPaneItemFactory;
import org.iocaste.appbuilder.common.factories.TableToolFactory;
import org.iocaste.appbuilder.common.factories.TextEditorFactory;
import org.iocaste.appbuilder.common.factories.TextFactory;
import org.iocaste.appbuilder.common.factories.TextFieldFactory;
import org.iocaste.appbuilder.common.factories.ButtonFactory;
import org.iocaste.appbuilder.common.factories.DashboardFactoryFactory;
import org.iocaste.appbuilder.common.factories.DashboardGroupFactory;
import org.iocaste.appbuilder.common.factories.DashboardItemFactory;
import org.iocaste.appbuilder.common.factories.DataFormFactory;
import org.iocaste.appbuilder.common.factories.ExpandBarFactory;
import org.iocaste.appbuilder.common.factories.FileUploadFactory;
import org.iocaste.appbuilder.common.factories.FormFactory;
import org.iocaste.appbuilder.common.factories.LinkFactory;
import org.iocaste.appbuilder.common.factories.ListBoxFactory;
import org.iocaste.appbuilder.common.factories.NavControlFactory;
import org.iocaste.appbuilder.common.factories.NodeListFactory;
import org.iocaste.appbuilder.common.factories.ParameterFactory;
import org.iocaste.appbuilder.common.factories.PrintAreaFactory;
import org.iocaste.appbuilder.common.factories.RadioButtonFactory;
import org.iocaste.appbuilder.common.factories.RadioGroupFactory;
import org.iocaste.appbuilder.common.factories.ReportToolFactory;
import org.iocaste.appbuilder.common.factories.SkipFactory;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolEntry;
import org.iocaste.appbuilder.common.tabletool.AbstractTableHandler;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolEntry;
import org.iocaste.shell.common.AbstractContext;

public class BuilderCustomView extends AbstractCustomView {
    private String view;
    private Map<ViewSpecItem.TYPES, SpecFactory> factories;
    
    public BuilderCustomView() {
        factories = new HashMap<>();
        factories.put(ViewSpecItem.TYPES.FORM,
                new FormFactory());
        factories.put(ViewSpecItem.TYPES.PAGE_CONTROL,
                new NavControlFactory());
        factories.put(ViewSpecItem.TYPES.TABBED_PANE,
                new TabbedPaneFactory());
        factories.put(ViewSpecItem.TYPES.TABBED_PANE_ITEM,
                new TabbedPaneItemFactory());
        factories.put(ViewSpecItem.TYPES.STANDARD_CONTAINER,
                new StandardContainerFactory());
        factories.put(ViewSpecItem.TYPES.TEXT_EDITOR,
                new TextEditorFactory());
        factories.put(ViewSpecItem.TYPES.TABLE_TOOL,
                new TableToolFactory());
        factories.put(ViewSpecItem.TYPES.DATA_FORM,
                new DataFormFactory());
        factories.put(ViewSpecItem.TYPES.DASHBOARD,
                new DashboardFactoryFactory());
        factories.put(ViewSpecItem.TYPES.DASHBOARD_GROUP,
                new DashboardGroupFactory());
        factories.put(ViewSpecItem.TYPES.DASHBOARD_ITEM,
                new DashboardItemFactory());
        factories.put(ViewSpecItem.TYPES.EXPAND_BAR,
                new ExpandBarFactory());
        factories.put(ViewSpecItem.TYPES.NODE_LIST,
                new NodeListFactory());
        factories.put(ViewSpecItem.TYPES.TEXT,
                new TextFactory());
        factories.put(ViewSpecItem.TYPES.LINK,
                new LinkFactory());
        factories.put(ViewSpecItem.TYPES.LISTBOX,
                new ListBoxFactory());
        factories.put(ViewSpecItem.TYPES.REPORT_TOOL,
                new ReportToolFactory());
        factories.put(ViewSpecItem.TYPES.FILE_UPLOAD,
                new FileUploadFactory());
        factories.put(ViewSpecItem.TYPES.BUTTON,
                new ButtonFactory());
        factories.put(ViewSpecItem.TYPES.RADIO_BUTTON,
                new RadioButtonFactory());
        factories.put(ViewSpecItem.TYPES.RADIO_GROUP,
                new RadioGroupFactory());
        factories.put(ViewSpecItem.TYPES.SKIP,
                new SkipFactory());
        factories.put(ViewSpecItem.TYPES.TEXT_FIELD,
                new TextFieldFactory());
        factories.put(ViewSpecItem.TYPES.PRINT_AREA,
                new PrintAreaFactory());
        factories.put(ViewSpecItem.TYPES.PARAMETER,
                new ParameterFactory());
    }
    
    private void buildItem(PageBuilderContext context,
            ViewComponents components, ViewSpecItem item) {
        SpecFactory factory;
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        
        factory = factories.get(types[item.getType()]);
        if (factory != null)
            factory.run(context, components, item);
        
        for (ViewSpecItem child : item.getItems())
            buildItem(context, components, child);
    }
    
    private final void download(PageBuilderContext context) throws Exception {
        File file = new File(context.downloaddata.fullname);
        FileInputStream fis = new FileInputStream(file);
        byte[] content = new byte[fis.available()];
        
        fis.read(content);
        fis.close();
        
        context.function.setContentType(context.downloaddata.contenttype);
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
        NavControl navcontrol;
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
            _context.function.unregisterValidators();
            viewspec.run(_context);
            viewctx = _context.getView(view);
            viewctx.reset();
            for (ViewSpecItem item : viewspec.getItems())
                buildItem(_context, viewctx.getComponents(), item);

            navcontrol = factories.get(ViewSpecItem.TYPES.PAGE_CONTROL).get();
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
            updateComponents(_context);
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
    
    private final void updateComponents(PageBuilderContext context) {
        ViewComponents components;
        
        components = context.getView(context.view.getPageName()).
                getComponents();
        for (TableToolEntry entry : components.tabletools.values())
            if (entry.update)
                AbstractTableHandler.setObject(entry.component, entry.data);
        
        for (ReportToolEntry entry :  components.reporttools.values())
            if (entry.update)
                entry.component.refresh();
    }
}