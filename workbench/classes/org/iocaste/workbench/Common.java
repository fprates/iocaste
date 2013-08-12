package org.iocaste.workbench;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.InputComponent;

public class Common {
    public static final void updateCurrentSource(Context context) {
        ExtendedObject header = context.project.header;
        InputComponent input = context.view.getElement("editor");
        ProjectPackage package_ = context.project.packages.get(
                header.getValue("PACKAGE"));
        Source source = package_.sources.get(header.getValue("CLASS"));
        
        source.code = input.get(); 
    }
}
