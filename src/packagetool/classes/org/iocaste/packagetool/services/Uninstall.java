package org.iocaste.packagetool.services;

import java.util.Set;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.services.installers.ModuleInstaller;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.Shell;

public class Uninstall extends AbstractHandler {
    public static final byte DOCS_LIB = 0;
    public static final byte SH_LIB = 1;
    public static final byte AUTH_LIB = 2;
    public static final byte CONFIG_LIB = 3;
    
    public final void run(String pkgname, Set<String> types) {
        String objecttype, style;
        Query query;
        ExtendedObject object;
        AbstractServiceInterface[] services;
        Services function = getFunction();
        ExtendedObject[] objects = Registry.getEntries(pkgname, function);
        
        if (objects == null)
            return;
        
        style = null;
        services = new AbstractServiceInterface[4];
        services[DOCS_LIB] = new Documents(function);
        services[AUTH_LIB] = new Authority(function);
        services[SH_LIB]= new SHLib(function);
        services[CONFIG_LIB] = new GlobalConfig(function);
        for (int i = objects.length; i > 0; i--) {
            object = objects[i - 1];
            objecttype = object.getst("MODEL");
            if ((types != null) && (!types.contains(objecttype)))
                continue;
            
            switch (objecttype) {
            case "STYLE":
                style = object.getst("NAME");
                break;
            }
            
            item(object, function, services);
        }
        
        new Iocaste(function).invalidateAuthCache();

        if (style != null)
            new Shell(function).invalidateStyle(style);
        
        if (types != null)
            return;
        
        query = new Query("delete");
        query.setModel("PACKAGE");
        query.andEqual("NAME", pkgname);
        ((Documents)services[DOCS_LIB]).update(query);
    }

    private static final void item(ExtendedObject object, Services function,
            AbstractServiceInterface... services) {
        Query query;
        ModuleInstaller installer;
        Documents documents = (Documents)services[DOCS_LIB];
        SHLib shlib = (SHLib)services[SH_LIB];
        Authority authority = (Authority)services[AUTH_LIB];
        GlobalConfig config = (GlobalConfig)services[CONFIG_LIB];
        String modeltype = object.get("MODEL");
        String name = object.get("NAME");
        
        switch (modeltype) {
        case "MESSAGE":
            InstallMessages.uninstall(object.getst("PACKAGE"), documents);
            documents.delete(object);
            return;
        case "SH":
            shlib.unassign(name);
            shlib.remove(name);
            documents.delete(object);
            return;
        case "TASK":
            query = new Query("delete");
            query.setModel("TASKS");
            query.andEqual("NAME", name);
            documents.update(query);
            documents.delete(object);
            return;
        case "NUMBER":
            documents.removeNumberFactory(name);
            documents.delete(object);
            return;
        case "AUTHORIZATION":
            authority.remove(name);
            documents.delete(object);
            return;
        case "AUTH_PROFILE":
            authority.removeProfile(name);
            documents.delete(object);
            return;
        case "TSKGROUP":
            Selector.removeGroup(name, documents);
            documents.delete(object);
            return;
        case "TSKITEM":
            Selector.removeTask(name, documents);
            documents.delete(object);
            return;
        case "CONFIG_ENTRY":
            config.remove(name);
            documents.delete(object);
            return;
        case "PACKAGE_TEXT":
            InstallTexts.uninstall(name, function);
            documents.delete(object);
            return;
        case "DATA_ELEMENT":
            documents.delete(object);
            return;
        default:
            installer = function.installers.get(modeltype);
            if (installer != null)
                installer.remove(services, object);
        }
    }

    @Override
    public Object run(Message message) throws Exception {
        String pkgname = message.getst("package");
        
        if (pkgname == null)
            throw new IocasteException("package name not specified.");
        
        run(pkgname, null);
        return null;
    }
}
