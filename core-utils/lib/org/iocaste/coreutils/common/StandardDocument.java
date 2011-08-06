package org.iocaste.coreutils.common;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DataComponent;
import org.iocaste.documents.common.Document;

public class StandardDocument implements Document {
    private static final long serialVersionUID = -6492481804815330422L;
    private List<DataComponent> components;
    
    public StandardDocument() {
        components = new ArrayList<DataComponent>();
    }
    
    @Override
    public DataComponent[] getComponents() {
        return components.toArray(new DataComponent[0]);
    }

}
