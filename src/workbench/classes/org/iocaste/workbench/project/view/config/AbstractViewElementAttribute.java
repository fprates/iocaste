package org.iocaste.workbench.project.view.config;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;

public abstract class AbstractViewElementAttribute
        implements ViewElementAttribute {
    private String name;
    private Context extcontext;
    private int type;
    
    public AbstractViewElementAttribute(String name, int type) {
        this.name = name;
        this.type = type;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public ExtendedObject instance(ExtendedObject spec, String value) {
        ExtendedObject object = extcontext.view.instance("config");
        
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", extcontext.view.getstKey());
        object.set("SPEC", spec.getst("ITEM_ID"));
        object.set("NAME", name);
        object.set("VALUE", value);
        object.set("TYPE", type);
        return object;
    }
    
    @Override
    public final void setContext(Context extcontext) {
        this.extcontext = extcontext;
    }
}