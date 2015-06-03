package org.iocaste.external;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.cmodelviewer.Context;
import org.iocaste.documents.common.ExtendedObject;

public class ExternalContext extends Context {
    public List<ExtendedObject> modelitems;
    
    public ExternalContext() {
        modelitems = new ArrayList<>();
    }
}
