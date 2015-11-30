package org.iocaste.appbuilder.common.factories;

import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public class TextEditorFactory extends AbstractSpecFactory {
    private TextEditor editor;

    @Override
    protected void execute() {
        editor = new TextEditorTool(context).instance(container, name);
        components.editors.put(name, editor);
    }
    
    public final TextEditor get() {
        return editor;
    }

}
