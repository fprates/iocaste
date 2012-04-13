package org.iocaste.packagetool;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;

public class Registry {

    /**
     * 
     * @param model
     * @param package_
     * @param documents
     * @param pkgid
     * @throws Exception
     */
    public static final void add(DocumentModel model, String package_,
            Documents documents, long pkgid) throws Exception {
        add(model.getName(), "MODEL", package_, documents, pkgid);
    }
    
    /**
     * 
     * @param name
     * @param model
     * @param pkgname
     * @param documents
     * @param pkgid
     * @throws Exception
     */
    public static final void add(String name, String model, String pkgname,
            Documents documents, long pkgid) throws Exception {
        ExtendedObject pkgitem = new ExtendedObject(
                documents.getModel("PACKAGE_ITEM"));
        
        pkgitem.setValue("CODE", pkgid);
        pkgitem.setValue("NAME", name);
        pkgitem.setValue("PACKAGE", pkgname);
        pkgitem.setValue("MODEL", model);
        
        documents.save(pkgitem);
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
