package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractViewSpec {
    private Map<String, ViewSpecItem> items;
    private List<ViewSpecItem> sequence;
    
    public AbstractViewSpec() {
        items = new HashMap<>();
        sequence = new ArrayList<>();
    }
    
    public abstract void execute();
    
    protected final void dataForm(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.DATA_FORM, name);
    }
    
    protected final void form(String name) {
        ViewSpecItem item = new ViewSpecItem(
                "view", ViewSpecItem.TYPES.FORM, name);
        items.put(name, item);
        sequence.add(item);
    }
    
    public final List<ViewSpecItem> getItems() {
        return sequence;
    }
    
    protected final void navControl(String parent) {
        put(parent, ViewSpecItem.TYPES.PAGE_CONTROL, "navcontrol");
    }
    
    private final void put(String parent, ViewSpecItem.TYPES type, String name)
    {
        ViewSpecItem item = new ViewSpecItem(parent, type, name);
        
        items.get(parent).add(item);
        items.put(name, item);
    }
    
    protected final void tabbedPane(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABBED_PANE, name);
    }
    
    protected final void tabbedPaneItem(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABBED_PANE_ITEM, name);
    }
    
    protected final void tableTool(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TABLE_TOOL, name);
    }
    
    protected final void standardContainer(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.STANDARD_CONTAINER, name);
    }
    
    protected final void textEditor(String parent, String name) {
        put(parent, ViewSpecItem.TYPES.TEXT_EDITOR, name);
    }
}
