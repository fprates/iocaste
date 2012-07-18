package org.iocaste.packagetool;

import org.iocaste.documents.common.ComplexModel;

public class InstallCModels {
    
    /**
     * 
     * @param cmodels
     * @param state
     */
    public static final void init(ComplexModel[] cmodels, State state) {
        for (ComplexModel cmodel : cmodels) {
            state.documents.create(cmodel);
            Registry.add(cmodel.getName(), "CMODEL", state);
        }
    }

}
