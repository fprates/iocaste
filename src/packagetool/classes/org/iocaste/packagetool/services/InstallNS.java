package org.iocaste.packagetool.services;

import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.NameSpace;

public class InstallNS {

    public static final void init(List<NameSpace> namespaces, State state) {
        for (NameSpace namespace : namespaces) {
            state.documents.createNameSpace(namespace);
            Registry.add(namespace.getName(), "NS", state);
        }
    }
    
    public static final void uninstall(String name, Documents documents) {
        documents.removeNameSpace(name);
    }
}
