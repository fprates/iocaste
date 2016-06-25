package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;

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
    
    public final void item(String name, String modeltag, int digits) {
        ComplexModelItem item;
        item = cmodel.put(name, context.getModel(modeltag).getModel());
        item.keydigits = digits;
    }
    
    public final void item(String name, String modeltag, String format) {
        ComplexModelItem item;
        item = cmodel.put(name, context.getModel(modeltag).getModel());
        item.keyformat = format;
    }
    
    public final void item(String name, ComplexModelInstall cmodelinst) {
        cmodel.put(name, cmodelinst.cmodel);
    }
}
