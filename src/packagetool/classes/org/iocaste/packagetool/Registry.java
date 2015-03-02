package org.iocaste.packagetool;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Function;

public class Registry {
    
    /**
     * 
     * @param name
     * @param model
     * @param state
     */
    public static final void add(String name, String model, State state) {
        ExtendedObject pkgitem = new ExtendedObject(
                state.documents.getModel("PACKAGE_ITEM"));
        String code;
        
        code = String.format("%s%03d", name, state.pkgitem++);
        pkgitem.set("CODE", code);
        pkgitem.set("NAME", name);
        pkgitem.set("PACKAGE", state.pkgname);
        pkgitem.set("MODEL", model);

        state.log.add(pkgitem);
    }
    
    /**
     * 
     * @param package_
     * @param function
     * @return
     */
    public static final ExtendedObject[] getEntries(String package_,
            Function function) {
        Query query = new Query();
        
        query.setModel("PACKAGE_ITEM");
        query.andEqual("PACKAGE", package_);
        return new Documents(function).select(query);
    }
}
