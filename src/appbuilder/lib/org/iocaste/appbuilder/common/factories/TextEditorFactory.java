package org.iocaste.appbuilder.common.factories;

import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public class TextEditorFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        TextEditor editor;
        
        editor = new TextEditorTool(context).instance(container, name);
        components.editors.put(name, editor);
    }

}
