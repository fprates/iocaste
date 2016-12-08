package org.iocaste.documents.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class SearchHelpData extends Query {
    private static final long serialVersionUID = -4719222126755931062L;
    private static final boolean LINK = true;
    private String export, name;
    private Map<String, SearchHelpColumn> items;
    
    public SearchHelpData(String name) {
        this.name = name;
        items = new LinkedHashMap<>();
    }

    public final void add(String item) {
        add(item, !LINK);
    }
    
    private final void add(String item, boolean link) {
        SearchHelpColumn shc = new SearchHelpColumn();
        
        shc.link = link;
        items.put(item, shc);
    }
    
    public final String getExport() {
        return export;
    }
    
    public final Map<String, SearchHelpColumn> getItems() {
        return items;
    }
    
    public final String getName() {
        return name;
    }
    
    public final void link(String item) {
        add(item, LINK);
    }
    
    public final void setExport(String export) {
        this.export = export;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
