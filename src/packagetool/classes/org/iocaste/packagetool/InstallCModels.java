package org.iocaste.packagetool;

import org.iocaste.documents.common.ComplexModel;

public class InstallCModels {
    
    /**
     * 
     * @param cmodels
     * @param state
     */
    public static final void init(ComplexModel[] cmodels, State state) {
        String name;
        
        for (ComplexModel cmodel : cmodels) {
            state.documents.create(cmodel);
            name = cmodel.getName();
            Registry.add(name, "CMODEL", state);
        }
    }
}
