package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.GlobalConfigData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;

public class Services extends AbstractFunction {

    public Services() {
        export("assign_task_group", "assignTaskGroup");
        export("install", "install");
        export("is_installed", "isInstalled");
        export("uninstall", "uninstall");
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void assignTaskGroup(Message message) throws Exception {
        ExtendedObject group;
        String groupname = message.getString("group");
        String username = message.getString("username");
        State state = new State();
        
        state.documents = new Documents(this);
        group = TaskSelector.getGroup(groupname, state);
        TaskSelector.assignGroup(group, username, state);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final Integer install(Message message) throws Exception {
        ExtendedObject header;
        DocumentModel tasks;
        Set<User> users;
        Set<GlobalConfigData> config;
        Map<UserProfile, Set<User>> profiles;
        Map<String, String> links;
        Map<TaskGroup, Set<User>> tasksgroups;
        DocumentModel[] models;
        ComplexModel[] cmodels;
        SearchHelpData[] shdata;
        Authorization[] authorizations;
        String[] dependencies;
        State state;
        String name, modelname;

        state = new State();
        state.data = message.get("data");
        state.pkgname = message.getString("name");
        state.documents = new Documents(this);
        state.pkgid = state.documents.getNextNumber("PKGCODE") * 1000000;
        state.shm = new HashMap<>();
        state.function = this;
        
        try {
            dependencies = state.data.getDependencies();
            if (dependencies != null)
                for (String pkgname : dependencies) {
                    if (isInstalled(pkgname))
                        continue;
                    
                    throw new Exception(new StringBuilder(state.pkgname).
                            append(": required package ").
                            append(pkgname).
                            append(" not installed.").toString());
                }

            /*
             * Registra instalação do pacote
             */
            header = new ExtendedObject(state.documents.getModel("PACKAGE"));
            header.setValue("NAME", state.pkgname);
            header.setValue("CODE", state.pkgid);
            state.documents.save(header);
            
            /*
             * gera modelos;
             * insere registros;
             * prepara dados para ajuda de pesquisa.
             */
            models = state.data.getModels();
            if (models.length > 0)
                InstallModels.init(models, state);
            
            state.documents.commit();
            cmodels = state.data.getCModels();
            if (cmodels.length > 0)
                InstallCModels.init(cmodels, state);
            
            /*
             * insere usuários
             */
            users = state.data.getUsers();
            if (users.size() > 0)
                InstallUsers.init(users, state);
            
            /*
             * registra objetos de numeração
             */
            for (String factory : state.data.getNumberFactories()) {
                state.documents.createNumberFactory(factory);
                Registry.add(factory, "NUMBER", state);
            }
            
            /*
             * gera ajudas de pesquisa
             */
            shdata = state.data.getSHData();
            if (shdata.length > 0)
                InstallSH.init(shdata, state);
            
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
            if (authorizations.length > 0)
                InstallAuthorizations.init(authorizations, state);
            
            profiles = state.data.getUserProfiles();
            if (profiles.size() > 0)
                InstallAuthorizations.init(profiles, state);
            
            new Iocaste(state.function).invalidateAuthCache();
            
            /*
             * registra tarefas
             */
            tasks = state.documents.getModel("TASKS");
            links = state.data.getLinks();
            if (links.size() > 0)
                InstallLinks.init(links, tasks, state);
            
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
             * grava itens instalados
             */
            for (ExtendedObject object : state.log)
                state.documents.save(object);
            
            return 1;
        } catch (Exception e) {
            state.documents.rollback();
            for (ExtendedObject object : state.log) {
                name = object.getValue("MODEL");
                if (name == null || !name.equals("MODEL"))
                    continue;
                
                modelname = object.getValue("NAME");
                state.documents.removeModel(modelname);
            }
            
            state.documents.commit();
            throw e;
        }
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final boolean isInstalled(Message message) {
        String pkgname = message.getString("package");
        
        return isInstalled(pkgname);
    }
    
    /**
     * 
     * @param pkgname
     * @return
     */
    private final boolean isInstalled(String pkgname) {
        ExtendedObject item = new Documents(this).
                getObject("PACKAGE", pkgname);
        
        return (item != null);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void uninstall(Message message) throws Exception {
        String pkgname = message.getString("package");
        
        if (pkgname == null)
            throw new IocasteException("package name not specified.");
        
        Uninstall.init(pkgname, this);
    }
}
