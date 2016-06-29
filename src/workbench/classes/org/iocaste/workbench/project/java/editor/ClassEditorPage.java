package org.iocaste.workbench.project.java.editor;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class ClassEditorPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new ClassEditorSpec());
        set(new ClassEditorConfig());
        set(new ClassEditorInput());
        action("save", new ClassEditorSave());
    }
    
}
