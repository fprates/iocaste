package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.StyleSheet;

public class PageBuilderContext extends AbstractContext {
    private Map<String, ViewContext> viewcontexts;
    public DownloadData downloaddata;
    public Object[][] ncsheet, ncspec, ncconfig;
    public StyleSheet stylesheet;
    public Map<String, Object[]> styles;
    public Map<String, Object[]> panels;
    
    public PageBuilderContext() {
        viewcontexts = new HashMap<>();
        styles = new HashMap<>();
        panels = new HashMap<>();
    }

    public final void add(
            String page, AbstractPanelPage panel) {
        add(page, panel, null);
    }
    
    public final void add(
            String page, AbstractPanelPage panel, ExtendedContext extcontext) {
        panels.put(page, new Object[] {panel, extcontext});
    }
    
    /**
     * 
     * @return
     */
    public final ViewContext getView() {
        return viewcontexts.get(view.getPageName());
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewContext getView(String name) {
        return viewcontexts.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final Set<String> getViews() {
        return viewcontexts.keySet();
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ViewContext instance(String name) {
        ViewContext context;
        
        context = new ViewContext(name);
        viewcontexts.put(name, context);
        return context;
    }
    
    public final void refreshStyle() {
        Object[] style = styles.get(view.getPageName());
        view.setStyleConst((Object[][])style[0]);
        view.setStyleSheet((Object[][])style[1]);
    }
    
    public final void run(String page, String action) throws Exception {
        this.action = action;
        getView(page).getActionHandler(action).run(this);
    }
    
    public final void storeStyle() {
        String name = view.getPageName();
        Object[] style = styles.get(name);
        if (style == null) {
            style = new Object[2];
            styles.put(name, style);
        }
        style[0] = view.getStyleConstants();
        style[1] = view.getStyleSheet();
    }
}