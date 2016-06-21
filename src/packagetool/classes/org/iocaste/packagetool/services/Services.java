package org.iocaste.packagetool.services;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("assign_task_group", "assignTaskGroup");
        export("install", new PackageInstall());
        export("is_installed", new IsInstalled());
        export("uninstall", new Uninstall());
        export("update", new PackageUpdate());
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void assignTaskGroup(Message message) throws Exception {
        ExtendedObject group;
        String groupname = message.getst("group");
        String username = message.getst("username");
        State state = new State();
        
        state.documents = new Documents(this);
        group = Selector.getGroup(groupname, state);
        Selector.assignGroup(group, username, state);
    }
}
