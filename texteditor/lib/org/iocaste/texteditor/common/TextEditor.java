package org.iocaste.texteditor.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class TextEditor implements Serializable {
    private static final long serialVersionUID = 8099830107603124518L;
    private int size;
    private String name;
    private Map<Long, String> pages;
    
    public TextEditor(String name) {
        this.name = name;
        size = 80;
        pages = new LinkedHashMap<>();
    }
    
    public final void commit(long page, String text) {
        pages.put(page, text);
    }
    
    public final int getLineSize() {
        return size;
    }
    
    public final String getName() {
        return name;
    }
    
    public final Long[] getPages() {
        return pages.keySet().toArray(new Long[0]);
    }
    
    public final String getString(long page) {
        return pages.get(page);
    }
    
    public final void setLineSize(int size) {
        this.size = size;
    }
}
