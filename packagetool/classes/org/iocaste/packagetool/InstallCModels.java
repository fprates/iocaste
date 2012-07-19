package org.iocaste.packagetool;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ExtendedObject;

public class InstallCModels {
    
    /**
     * 
     * @param cmodels
     * @param state
     */
    public static final void init(ComplexModel[] cmodels, State state) {
        ExtendedObject object;
        String name;
        
        for (ComplexModel cmodel : cmodels) {
            state.documents.create(cmodel);
            name = cmodel.getName();
            Registry.add(name, "CMODEL", state);
            
            object = state.documents.getObject("COMPLEX_MODEL", name);
            name = object.getValue("CD_LINK");
            Registry.add(name, "MODEL", state);
        }
    }
}
