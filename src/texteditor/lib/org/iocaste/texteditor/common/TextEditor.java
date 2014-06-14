package org.iocaste.texteditor.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.shell.common.TextArea;

public class TextEditor implements Serializable {
    private static final long serialVersionUID = 8099830107603124518L;
    private String name;
    private Map<String, String> pages;
    private TextArea element;
    
    public TextEditor(String name) {
        this.name = name;
        pages = new LinkedHashMap<>();
    }
    
    public final void commit(String id, String text) {
        pages.put(id, text);
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
    
    public final String[] getPages() {
        return pages.keySet().toArray(new String[0]);
    }
    
    public final String getString(String id) {
        return pages.get(id);
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
