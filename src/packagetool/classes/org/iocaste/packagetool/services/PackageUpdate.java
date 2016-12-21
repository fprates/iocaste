package org.iocaste.packagetool.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class PackageUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Uninstall uninstall;
        Set<String> types;
        Map<String, String> links;
        Map<TaskGroup, Set<User>> tasksgroups;
        DocumentModel tasks;
        State state;
        Services services;
        
        state = new State();
        state.data = message.get("data");
        state.function = getFunction();
        state.documents = new Documents(state.function);
        state.pkgname = message.getst("name");
        
        services = getFunction();
        for (String key : services.installers.keySet())
            services.installers.get(key).update(state);
        
        types = new HashSet<>();
        types.add("MESSAGE");
        types.add("TSKGROUP");
        types.add("TSKITEM");
        types.add("TASK");
        
        uninstall = services.get("uninstall");
        uninstall.run(state.pkgname, types);
        
        new Iocaste(state.function).invalidateAuthCache();

        links = state.data.getLinks();
        if (links.size() > 0) {
            tasks = state.documents.getModel("TASKS");
            InstallLinks.init(links, tasks, state);
        }
        
        tasksgroups = state.data.getTasksGroups();
        if (tasksgroups.size() > 0)
            InstallTasksGroups.init(tasksgroups, state);
        
        /*
         * grava itens instalados
         */
        for (ExtendedObject object : state.log)
            state.documents.save(object);
        
        return null;
    }

}
