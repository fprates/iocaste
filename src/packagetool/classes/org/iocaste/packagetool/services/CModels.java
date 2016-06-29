package org.iocaste.packagetool.services;

import org.iocaste.documents.common.ComplexModel;

public class CModels {
    
    /**
     * 
     * @param cmodels
     * @param state
     */
    public static final void install(ComplexModel[] cmodels, State state) {
        String name;
        
        for (ComplexModel cmodel : cmodels) {
            name = cmodel.getName();
            if (state.documents.getComplexModel(name) != null)
                continue;
            state.documents.create(cmodel);
            Registry.add(name, "CMODEL", state);
        }
    }
    
    public static final void update(ComplexModel[] cmodels, State state) {
        String name;
        
        for (ComplexModel cmodel : cmodels) {
            name = cmodel.getName();
            if (state.documents.getComplexModel(name) != null)
                state.documents.update(cmodel);
        }
    }
}
