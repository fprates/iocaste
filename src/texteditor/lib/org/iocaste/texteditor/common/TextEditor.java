package org.iocaste.texteditor.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.TextArea;

public class TextEditor implements Serializable {
    private static final long serialVersionUID = 8099830107603124518L;
    private String name;
    private AbstractContext context;
    private Map<String, String> pages;
    
    public TextEditor(Container container, AbstractContext context, String name)
    {
        this.name = name;
        this.context = context;
        new TextArea(container, name);
        pages = new LinkedHashMap<>();
    }
    
    public final void commit(String id, String text) {
        pages.put(id, text);
    }
    
    public final TextArea getElement() {
        return context.view.getElement(name);
    }
    
    public final int getHeight() {
        return getElement().getHeight();
    }
    
    public final int getWidth() {
        return getElement().getWidth();
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
    
    public final void setHeight(int height) {
        TextArea area= getElement();
        area.setSize(area.getWidth(), height);
    }
    
    public final void setVisible(boolean visible) {
        getElement().setVisible(visible);
    }
    
    public final void setWidth(int width) {
        TextArea area = getElement();
        area.setSize(width, area.getHeight());
    }
}
