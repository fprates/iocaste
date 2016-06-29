package org.iocaste.workbench.project.java.editor;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ClassEditorSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform(parent, "head");
        texteditor(parent, "source");
    }
    
}
