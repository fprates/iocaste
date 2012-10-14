package org.iocaste.packagetool;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.SHLib;

public class Uninstall {
    private static final byte DEL_MESSAGES = 0;
    private static final byte DEL_PKG_ITEM = 1;
    private static final byte DEL_TASKS = 2;
    private static final byte DEL_PACKAGE = 3;
    private static final String[] QUERIES = {
        "delete from MESSAGES where PACKAGE = ?",
        "delete from PACKAGE_ITEM where PACKAGE = ? and MODEL = ?",
        "delete from TASKS where NAME = ?",
        "delete from PACKAGE where NAME = ?"
    };
    private static final byte DOCS_LIB = 0;
    private static final byte SH_LIB = 1;
    private static final byte AUTH_LIB = 2;
    private static final byte CONFIG_LIB = 3;
    
    public static final void init(String pkgname, Function function) {
        ExtendedObject object;
        AbstractServiceInterface[] services;
        ExtendedObject[] objects = Registry.getEntries(pkgname, function);
        
        if (objects == null)
            return;
        
        services = new AbstractServiceInterface[4];
        services[DOCS_LIB] = new Documents(function);
        services[AUTH_LIB] = new Authority(function);
        services[SH_LIB]= new SHLib(function);
        services[CONFIG_LIB] = new GlobalConfig(function);
        for (int i = objects.length; i > 0; i--) {
            object = objects[i - 1];
            item(object, services);
        }
            
        new Iocaste(function).invalidateAuthCache();
        ((Documents)services[DOCS_LIB]).update(QUERIES[DEL_PACKAGE], pkgname);
    }

    private static final void item(ExtendedObject object,
            AbstractServiceInterface... services) {
        Documents documents = (Documents)services[DOCS_LIB];
        SHLib shlib = (SHLib)services[SH_LIB];
        Authority authority = (Authority)services[AUTH_LIB];
        GlobalConfig config = (GlobalConfig)services[CONFIG_LIB];
        String modeltype = object.getValue("MODEL");
        String name = object.getValue("NAME");
        
        if (modeltype.equals("MESSAGE")) {
            name = object.getValue("PACKAGE");
            documents.update(QUERIES[DEL_MESSAGES], name);
            documents.update(QUERIES[DEL_PKG_ITEM], name, "MESSAGE");
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("SH")) {
            shlib.unassign(name);
            shlib.remove(name);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("TASK")) {
            documents.update(QUERIES[DEL_TASKS], name);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("MODEL")) {
            documents.removeModel(name);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("NUMBER")) {
            documents.removeNumberFactory(name);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("AUTHORIZATION")) {
            authority.remove(name);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("TSKGROUP")) {
            TaskSelector.removeGroup(name, documents);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("TSKITEM")) {
            TaskSelector.removeTask(name, documents);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("CMODEL")) {
            documents.removeComplexModel(name);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("CONFIG_ENTRY")) {
            config.remove(name);
            documents.delete(object);
            return;
        }
        
        if (modeltype.equals("DATA_ELEMENT"))
            documents.delete(object);
    }
}
