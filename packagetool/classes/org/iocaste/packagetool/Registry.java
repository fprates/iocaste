package org.iocaste.packagetool;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;

public class Registry {

    /**
     * 
     * @param state
     * @throws Exception
     */
    public static final void add(String name, State state) throws Exception {
        add(name, "MODEL", state);
    }
    
    /**
     * 
     * @param name
     * @param model
     * @param state
     * @throws Exception
     */
    public static final void add(String name, String model, State state)
            throws Exception {
        ExtendedObject pkgitem = new ExtendedObject(
                state.documents.getModel("PACKAGE_ITEM"));
        
        pkgitem.setValue("CODE", state.pkgid);
        pkgitem.setValue("NAME", name);
        pkgitem.setValue("PACKAGE", state.pkgname);
        pkgitem.setValue("MODEL", model);

        state.pkgid++;
        state.documents.save(pkgitem);
    }
    
    /**
     * 
     * @param package_
     * @param function
     * @return
     * @throws Exception
     */
    public static final ExtendedObject[] getEntries(String package_,
            Function function) throws Exception {
        Documents documents = new Documents(function);
        String query = "from PACKAGE_ITEM where PACKAGE = ?";
        
        return documents.select(query, package_);
    }
}
