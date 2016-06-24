package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.ComplexModel;

public class ComplexModelInstall {
    private StandardInstallContext context;
    private ComplexModel cmodel;
    
    public ComplexModelInstall(String name, StandardInstallContext context) {
        this.context = context;
        cmodel = new ComplexModel(name);
        context.getInstallData().add(cmodel);
    }
    
    public final void header(String modeltag) {
        cmodel.setHeader(context.getModel(modeltag).getModel());
    }
    
    public final void item(String name, String modeltag) {
        cmodel.put(name, context.getModel(modeltag).getModel());
    }
    
    public final void item(String name, ComplexModelInstall cmodelinst) {
        cmodel.put(name, cmodelinst.cmodel);
    }
}
