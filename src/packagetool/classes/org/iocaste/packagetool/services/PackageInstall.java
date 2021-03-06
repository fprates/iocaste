package org.iocaste.packagetool.services;

import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.packagetool.services.installers.ModuleInstaller;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.User;

public class PackageInstall extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        IsInstalled isinstalled;
        DocumentModel tasks;
        Map<String, String> links;
        Map<TaskGroup, Set<User>> tasksgroups;
        String[] dependencies;
        State state;
        Services function;
        ModuleInstaller installer;
        Documents documents;
        
        state = new State();
        state.data = message.get("data");
        state.pkgname = message.getst("name");
        state.function = getFunction();
        
        documents = state.documents = new Documents(state.function);
        isinstalled = state.function.get("is_installed");
        try {
            dependencies = state.data.getDependencies();
            if (dependencies != null)
                for (String pkgname : dependencies) {
                    if (isinstalled.run(pkgname))
                        continue;
                    throw new IocasteException(
                        "%s: required package %s not installed.",
                            state.pkgname, pkgname);
                }

            function = getFunction();
            for (String key : function.installers.keySet()) {
                installer = function.installers.get(key);
                installer.init(state.function);
                installer.install(state);
                state.installed++;
            }
            
            for (DataElement element : state.data.getElements())
                Registry.add(element.getName(), "DATA_ELEMENT", state);
            
            new Iocaste(state.function).invalidateAuthCache();
            
            /*
             * registra tarefas
             */
            links = state.data.getLinks();
            if (links.size() > 0) {
                tasks = documents.getModel("TASKS");
                InstallLinks.init(links, tasks, state);
            }
            
            tasksgroups = state.data.getTasksGroups();
            if (tasksgroups.size() > 0)
                InstallTasksGroups.init(tasksgroups, state);
            
            /*
             * grava itens instalados
             */
            for (ExtendedObject object : state.log)
                documents.save(object);
            
            return 1;
        } catch (Exception e) {
            if (state.installed > 1)
                documents.rollback();
            throw e;
        }
    }
}
