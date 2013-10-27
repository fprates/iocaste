package org.iocaste.workbench;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.InputComponent;

public class Common {
    public static final byte SEL_PACKAGES = 0;
    public static final byte SEL_SOURCES = 1;
    public static final byte DEL_SRCCODE = 3;
    public static final byte DEL_SOURCES = 4;
    public static final byte DEL_PACKAGES = 5;
    public static final String[] QUERIES = {
        "from ICSTPRJ_PACKAGES where PROJECT = ?",
        "from ICSTPRJ_SOURCES where PACKAGE = ?",
        "from ICSTPRJ_SRCCODE where SOURCE = ?",
        "delete from ICSTPRJ_SRCCODE where PROJECT = ?",
        "delete from ICSTPRJ_SOURCES where PROJECT = ?",
        "delete from ICSTPRJ_PACKAGES where PROJECT = ?"
    };
    
    public static final void updateCurrentSource(Context context) {
//        ExtendedObject header = context.project.header;
//        InputComponent input = context.view.getElement("editor");
//        ProjectPackage package_ = context.project.packages.get(
//                header.get("PACKAGE"));
//        Source source = package_.sources.get(header.get("CLASS"));
//        
//        source.code = input.get(); 
    }
}
