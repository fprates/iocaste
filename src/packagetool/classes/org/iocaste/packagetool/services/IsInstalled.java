package org.iocaste.packagetool.services;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class IsInstalled extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String pkgname = message.getString("package");
        
        return run(pkgname);
    }
    
    /**
     * 
     * @param pkgname
     * @return
     */
    public final boolean run(String pkgname) {
        ExtendedObject item = new Documents(getFunction()).
                getObject("PACKAGE", pkgname);
        
        return (item != null);
    }

}
