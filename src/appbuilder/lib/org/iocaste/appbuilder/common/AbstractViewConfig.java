package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public abstract class AbstractViewConfig implements ViewConfig {
    private PageBuilderContext context;
    private NavControl navcontrol;
    
    /**
     * 
     * @param viewconfig
     */
    protected final void autoconfig(ViewConfig viewconfig) {
        viewconfig.setNavControl(navcontrol);
        viewconfig.run(context);
    }
    
    /**
     * 
     * @param context
     */
    protected abstract void execute(PageBuilderContext context);
    
    protected final DashboardFactory getDashboard(String name) {
        return getViewComponents().dashboards.get(name);
    }
    
    /**
     * 
     * @param dashboard
     * @param name
     * @return
     */
    protected final DashboardComponent getDashboardItem(
            String dashboard, String name) {
        DashboardComponent component;
        ViewComponents components = getViewComponents();
        DashboardFactory factory = components.dashboards.get(dashboard);
        
        if (factory == null) {
            component = components.dashboardgroups.get(dashboard);
            if (component == null)
                throw new RuntimeException(
                        dashboard.concat(" is an invalid dashboard factory."));
            component = component.getFactory().get(name);
        } else {
            component = factory.get(name);
        }
        
        if (component == null)
            throw new RuntimeException(
                    name.concat(" is an invalid dashboard component."));
        return component;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getView(context.view.getPageName()).
                getExtendedContext();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }
    
    /**
     * 
     * @return
     */
    protected final NavControl getNavControl() {
        return navcontrol;
    }
    
    /**
     * 
     * @param tab
     * @param name
     * @return
     */
    protected final TabbedPaneItem getTabbedItem(String tab, String name) {
        return ((TabbedPane)getElement(tab)).getElement(name);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final ReportToolData getReportTool(String name) {
        return getViewComponents().reporttools.get(name).data;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final TableToolData getTableTool(String name) {
        return getViewComponents().tabletools.get(name).data;
    }
    
    /**
     * 
     * @return
     */
    private final ViewComponents getViewComponents() {
        return context.getView(context.view.getPageName()).getComponents();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#run(
     *    org.iocaste.appbuilder.common.PageBuilderContext)
     */
    @Override
    public final void run(PageBuilderContext context) {
        this.context = context;
        execute(context);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#setNavControl(
     *    org.iocaste.shell.common.NavControl)
     */
    @Override
    public final void setNavControl(NavControl navcontrol) {
        this.navcontrol = navcontrol;
    }
}
