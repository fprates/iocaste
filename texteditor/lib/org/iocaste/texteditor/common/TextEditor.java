package org.iocaste.texteditor.common;

import java.io.Serializable;

import org.iocaste.shell.common.TextArea;

public class TextEditor implements Serializable {
    private static final long serialVersionUID = 8099830107603124518L;
    private TextArea textarea;
    private String id;
    
    public TextEditor(TextArea textarea) {
        this.textarea = textarea;
    }
    
    public final String getId() {
        return id;
    }
    
    public final String getString() {
        return textarea.getText();
    }
    
    public final void setId(String id) {
        this.id = id;
    }
}
