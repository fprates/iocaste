package org.iocaste.appbuilder.common;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.docmanager.common.Manager;

public abstract class AbstractViewSpec {
    private Map<String, ViewSpecItem> items;
    private PageBuilderContext context;
    private boolean initialized;
    private int skipnr;
    
    public AbstractViewSpec() {
        items = new LinkedHashMap<>();
    }
    
    protected final void button(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.BUTTON, name);
    }
    
    protected final void dashboard(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.DASHBOARD, name);
    }
    
    protected final void dashboardgroup(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.DASHBOARD_GROUP, name);
    }
    
    protected final void dashboarditem(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.DASHBOARD_ITEM, name);
    }
    
    protected final void dataform(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.DATA_FORM, name);
    }
    
    protected abstract void execute();
    
    protected final void expandbar(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.EXPAND_BAR, name);
    }
    
    protected final void fileupload(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.FILE_UPLOAD, name);
    }
    
    protected final void form(String name) {
        ViewSpecItem item = new ViewSpecItem(
                "view", ViewSpecItem.TYPES.FORM, name);
        items.put(name, item);
    }
    
    public final ViewSpecItem get(String name) {
        return items.get(name);
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getView(context.view.getPageName()).
                getExtendedContext();
    }
    
    public final Collection<ViewSpecItem> getItems() {
        return items.values();
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }
    
    public final boolean isInitialized() {
        return initialized;
    }
    
    protected final void link(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.LINK, name);
    }
    
    protected final void listbox(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.LISTBOX, name);
    }
    
    protected final void navcontrol(String parent) {
        put(parent, ViewSpecItem.TYPES.PAGE_CONTROL, "navcontrol");
    }
    
    protected final void navcontrol(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.PAGE_CONTROL, name);
    }
    
    protected final void nodelist(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.NODE_LIST, name);
    }
    
    protected final void print(String line) {
        context.view.print(line);
    }
    
    private final void put(String parent, ViewSpecItem.TYPES type, String name)
    {
        ViewSpecItem item = new ViewSpecItem(parent, type, name);
        
        items.get(parent).add(item);
        items.put(name, item);
    }
    
    protected final void reporttool(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.REPORT_TOOL, name);
    }
    
    protected final void radiobutton(String parent, String group, String name)
    {
        put(parent, ViewSpecItem.TYPES.RADIO_BUTTON,
                new StringBuilder(group).append(".").append(name).toString());
    }
    
    protected final void radiogroup(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.RADIO_GROUP, name);
    }
    
    public final void run(PageBuilderContext context) {
        this.context = context;
        items.clear();
        execute();
    }
    
    public final void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    
    public final void skip(String parent) {
        put(parent, ViewSpecItem.TYPES.SKIP, new StringBuilder("skip").
                append(skipnr++).toString());
    }
    
    protected final void standardcontainer(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.STANDARD_CONTAINER, name);
    }
    
    protected final void tabbedpane(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABBED_PANE, name);
    }
    
    protected final void tabbedpaneitem(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABBED_PANE_ITEM, name);
    }
    
    protected final void tabletool(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABLE_TOOL, name);
    }
    
    protected final void text(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TEXT, name);
    }
    
    protected final void texteditor(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TEXT_EDITOR, name);
    }
}
