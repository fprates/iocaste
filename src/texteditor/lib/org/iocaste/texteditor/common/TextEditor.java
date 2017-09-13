package org.iocaste.texteditor.common;

import java.io.Serializable;

import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class TextEditor implements Serializable {
    private static final long serialVersionUID = 8099830107603124518L;
    private ToolData tooldata;
    
    public TextEditor(Container container, AbstractContext context, String name)
    {
        tooldata = new ToolData(TYPES.TEXT_EDITOR, name);
        new TextArea(container, name);
    }
    
    public final void commit(String id, String text) {
        tooldata.values.put(id, text);
    }
    
    public final TextArea getElement(AbstractContext context) {
        return context.view.getElement(tooldata.name);
    }
    
    public final String getName() {
        return tooldata.name;
    }
    
    public final String[] getPages() {
        return tooldata.values.keySet().toArray(new String[0]);
    }
    
    public final String getString(String id) {
        return (String)tooldata.values.get(id);
    }
    
    public final ToolData getToolData() {
        return tooldata;
    }
    
    public final void setHeight(AbstractContext context, int height) {
        TextArea area= getElement(context);
        area.setSize(area.getWidth(), height);
    }
    
    public final void setWidth(AbstractContext context, int width) {
        TextArea area = getElement(context);
        area.setSize(width, area.getHeight());
    }
}
