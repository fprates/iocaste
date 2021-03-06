package org.iocaste.runtime.common.install;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;

public class ComplexModelInstall {
    private InstallContext context;
    private ComplexModel cmodel;
    
    public ComplexModelInstall(String name, InstallContext context) {
        this.context = context;
        cmodel = new ComplexModel(name);
        context.getInstallData().add(cmodel);
    }
    
    public final ComplexModelItem document(String name, String cmodeltag) {
        return document(name, context.getCModel(cmodeltag));
    }
    
    public final ComplexModelItem document(
            String name, ComplexModelInstall cmodelinst) {
        return cmodel.put(name, cmodelinst.cmodel);
    }
    
    public final void header(String modeltag) {
        cmodel.setHeader(context.getModel(modeltag).getModel());
    }
    
    public final ComplexModelItem item(String name, String modeltag) {
        return cmodel.put(name, context.getModel(modeltag).getModel());
    }
}
