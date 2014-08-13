package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public abstract class AbstractViewConfig implements ViewConfig {
    private PageBuilderContext context;
    private NavControl navcontrol;
    private Map<String, Manager> managers;
    private Manager manager;
    
    /**
     * 
     * @param viewconfig
     */
    protected final void autoconfig(ViewConfig viewconfig) {
        viewconfig.setNavControl(navcontrol);
        viewconfig.setManagers(managers);
        viewconfig.run(context);
    }
    
    /**
     * 
     * @param context
     */
    protected abstract void execute(PageBuilderContext context);
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getExtendedContext(context.view.getPageName());
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
        DashboardFactory factory = getViewComponents().dashboards.
                get(dashboard);
        
        if (factory == null)
            throw new RuntimeException(
                    dashboard.concat(" is an invalid dashboard factory."));
        
        component = factory.get(name);
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
    protected final Manager getManager() {
        return manager;
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
    protected final TableToolData getTableTool(String name) {
        return getViewComponents().tabletools.get(name).data;
    }
    
    /**
     * 
     * @return
     */
    private final ViewComponents getViewComponents() {
        return context.getViewComponents(context.view.getPageName());
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
     * @see org.iocaste.appbuilder.common.ViewConfig#setManager(
     *    org.iocaste.docmanager.common.Manager)
     */
    @Override
    public final void setManagers(Map<String, Manager> managers) {
        this.managers = managers;
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
    
    protected final void useManager(String name) {
        manager = managers.get(name);
    }

}
