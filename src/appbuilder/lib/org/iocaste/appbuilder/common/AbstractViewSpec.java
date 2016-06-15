package org.iocaste.appbuilder.common;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.docmanager.common.Manager;

public abstract class AbstractViewSpec implements ViewSpec {
    protected String parent;
    private Map<String, ViewSpecItem> items;
    private PageBuilderContext context;
    private boolean initialized;
    private ExtendedContext extcontext;
    
    public AbstractViewSpec() {
        items = new LinkedHashMap<>();
    }
    
    protected final void button(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.BUTTON, name);
    }
    
    protected final void dataform(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.DATA_FORM, name);
        if (extcontext != null)
            extcontext.dataformInstance(name);
    }
    
    protected abstract void execute(PageBuilderContext context);
    
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
    
    @Override
    @SuppressWarnings("unchecked")
    public final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getView(context.view.getPageName()).
                getExtendedContext();
    }

    @Override
    public final Collection<ViewSpecItem> getItems() {
        return items.values();
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }

    @Override
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
    
    protected final void nodelistitem(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.NODE_LIST_ITEM, name);
    }
    
    protected final void parameter(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.PARAMETER, name);
    }
    
    protected final void printarea(String parent) {
        put(parent, ViewSpecItem.TYPES.PRINT_AREA, "printarea");
    }
     
    private final void put(String parent, ViewSpecItem.TYPES type, String name)
    {
        ViewSpecItem item = new ViewSpecItem(parent, type, name);
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
        run(null, context);
    }
    
    public final void run(ViewSpecItem item, PageBuilderContext context) {
        this.context = context;
        items.clear();
        if (item != null) {
            parent = item.getName();
            items.put(parent, item);
        }
        extcontext = context.getView().getExtendedContext();
        execute(context);
    }

    @Override
    public final void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
    
    protected final void spec(String parent, ViewSpec spec) {
        spec.run(items.get(parent), context);
        for (ViewSpecItem item : spec.getItems())
            items.put(item.getName(), item);
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
        if (extcontext != null)
            extcontext.tableInstance(name);
    }
    
    protected final void text(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TEXT, name);
    }
    
    protected final void texteditor(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TEXT_EDITOR, name);
    }
    
    protected final void textfield(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TEXT_FIELD, name);
    }
    
    protected final void tiles(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TILES, name);
    }
}
