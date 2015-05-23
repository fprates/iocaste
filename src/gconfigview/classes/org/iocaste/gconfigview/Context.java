package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public static final byte DISPLAY = 0;
    public static final byte EDIT = 1;
    public static final byte SELECT = 2;
    public static final String[] TITLES = {
        "config.display",
        "config.edit",
        "config.select"
    };
    
    public ExtendedObject[] objects;
    public String appname;
    public DocumentModel globalcfgmodel;
    
    public Context(PageBuilderContext context) {
        globalcfgmodel = new Documents(context.function).
                getModel("GLOBAL_CONFIG");
    }
}
