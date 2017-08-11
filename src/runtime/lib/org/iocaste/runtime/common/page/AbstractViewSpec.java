package org.iocaste.runtime.common.page;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public abstract class AbstractViewSpec implements ViewSpec {
    protected String parent;
    private Map<String, ViewSpecItem> items;
    private boolean initialized;
    private Context context;
    
    public AbstractViewSpec() {
        items = new LinkedHashMap<>();
    }
    
    protected final void button(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.BUTTON, name);
    }
    
    protected final void component(
            ViewSpecItem.TYPES type, String parent, String name) {
        put(parent, type, name);
    }
    
    protected final void dataform(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.DATA_FORM, name);
    }
    
    protected abstract void execute(Context context);
    
    protected final void expandbar(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.EXPAND_BAR, name);
    }
    
    protected final void fileupload(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.FILE_UPLOAD, name);
    }
    
    protected final void form(String name) {
    	put("view", ViewSpecItem.TYPES.FORM, name);
    }
    
    protected final void frame(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.FRAME, name);
    }
    
    @Override
    public final ViewSpecItem get(String name) {
        return items.get(name);
    }

    @Override
    public final Collection<ViewSpecItem> getItems() {
        return items.values();
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
     
    public final void put(String parent, TYPES type, String name) {
        ViewSpecItem item = new ViewSpecItem(parent, type, name);
        if (this.parent == null)
        	items.put(name, item);
        else
        	context.getPage().getSpec().put(parent, type, name);
    }
    
    protected final void reporttool(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.REPORT_TOOL, name);
    }
    
    protected final void radiobutton(String parent, String group, String name)
    {
    	String specname =
    			new StringBuilder(group).append(".").append(name).toString();
        put(parent, ViewSpecItem.TYPES.RADIO_BUTTON, specname);
        context.getPage().addSpecAlias(name, specname);
    }
    
    protected final void radiogroup(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.RADIO_GROUP, name);
    }
    
    public final void run(Context context) {
        run(null, context);
    }
    
    public final void run(ViewSpecItem item, Context context) {
        items.clear();
        if (item != null) {
            parent = item.getName();
            items.put(parent, item);
        }
        this.context = context;
        execute(context);
        setInitialized(true);
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
