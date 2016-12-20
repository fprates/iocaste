package org.iocaste.packagetool.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.GlobalConfigData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;

public class PackageInstall extends AbstractHandler {
    
    @Override
    public Object run(Message message) throws Exception {
        IsInstalled isinstalled;
        DocumentModel tasks;
        Set<User> users;
        Set<GlobalConfigData> config;
        Map<UserProfile, Set<User>> profiles;
        Map<String, String> links;
        Map<TaskGroup, Set<User>> tasksgroups;
        List<Authorization> authorizations;
        String[] dependencies;
        State state;
        Set<String> texts;
        Map<String, Map<String, Long>> numbers;
        Services function;
        
        state = new State();
        state.data = message.get("data");
        state.pkgname = message.getst("name");
        state.function = getFunction();
        state.documents = new Documents(state.function);
        
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
            for (String key : function.installers.keySet())
                function.installers.get(key).install(state);
            
            state.installed++;
            /*
             * insere usuários
             */
            users = state.data.getUsers();
            if (users.size() > 0)
                InstallUsers.init(users, state);
            
            /*
             * registra objetos de numeração
             */
            numbers = state.data.getNumberFactories();
            for (String factory : numbers.keySet()) {
                state.documents.createNumberFactory(
                        factory, null, numbers.get(factory));
                Registry.add(factory, "NUMBER", state);
            }
            
            for (DataElement element : state.data.getElements())
                Registry.add(element.getName(), "DATA_ELEMENT", state);
            
            /*
             * registra mensagens
             */
            state.messages = state.data.getMessages();
            if (state.messages.size() > 0)
                InstallMessages.init(state);
            
            /*
             * instala autorizações, perfis
             */
            authorizations = state.data.getAuthorizations();
            if (authorizations.size() > 0)
                InstallAuthorizations.init(authorizations, state);
            
            profiles = state.data.getUserProfiles();
            if (profiles.size() > 0)
                InstallAuthorizations.init(profiles, state);
            
            new Iocaste(state.function).invalidateAuthCache();
            
            /*
             * registra tarefas
             */
            links = state.data.getLinks();
            if (links.size() > 0) {
                tasks = state.documents.getModel("TASKS");
                InstallLinks.init(links, tasks, state);
            }
            
            tasksgroups = state.data.getTasksGroups();
            if (tasksgroups.size() > 0)
                InstallTasksGroups.init(tasksgroups, state);
        
            /*
             * registra parâmetros de configuração
             */
            config = state.data.getGlobalConfigs();
            if (config.size() > 0)
                InstallGlobalConfig.init(config, state);
            
            /*
             * registra textos da aplicação
             */
            texts = state.data.getTexts();
            if (texts.size() > 0)
                InstallTexts.init(texts, state);
            
            /*
             * grava itens instalados
             */
            for (ExtendedObject object : state.log)
                state.documents.save(object);
            
            return 1;
        } catch (Exception e) {
            if (state.installed > 1)
                state.documents.rollback();
            throw e;
        }
    }
}
