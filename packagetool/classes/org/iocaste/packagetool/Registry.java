package org.iocaste.packagetool;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class Registry {

    /**
     * 
     * @param model
     * @param package_
     * @param documents
     * @throws Exception
     */
    public static final void add(DocumentModel model, String package_,
            Documents documents) throws Exception {
        add(model.getName(), "MODEL", package_, documents);
    }
    
    /**
     * 
     * @param name
     * @param model
     * @param package_
     * @param documents
     * @throws Exception
     */
    public static final void add(String name, String model, String package_,
            Documents documents) throws Exception {
        ExtendedObject pkgitem = new ExtendedObject(
                documents.getModel("PACKAGE_ITEM"));
        String item = new StringBuilder(name).append("@").append(model).
                toString();
        
        pkgitem.setValue("NAME", item);
        pkgitem.setValue("PACKAGE", package_);
        
        documents.save(pkgitem);
    }
}
