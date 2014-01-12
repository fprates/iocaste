package org.iocaste.texteditor.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.shell.common.TextArea;

public class TextEditor implements Serializable {
    private static final long serialVersionUID = 8099830107603124518L;
    private String name;
    private Map<Long, String> pages;
    private TextArea element;
    
    public TextEditor(String name) {
        this.name = name;
        pages = new LinkedHashMap<>();
    }
    
    public final void commit(long page, String text) {
        pages.put(page, text);
    }
    
    public final TextArea getElement() {
        return element;
    }
    
    public final int getHeight() {
        return element.getHeight();
    }
    
    public final int getWidth() {
        return element.getWidth();
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
    
    public final void setElement(TextArea element) {
        this.element = element;
    }
    
    public final void setHeight(int height) {
        element.setSize(element.getWidth(), height);
    }
    
    public final void setVisible(boolean visible) {
        element.setVisible(visible);
    }
    
    public final void setWidth(int width) {
        element.setSize(width, element.getHeight());
    }
}
