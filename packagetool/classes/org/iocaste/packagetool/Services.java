package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;
import org.iocaste.shell.common.SHLib;

public class Services extends AbstractFunction {
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
        Map<UserProfile, Set<User>> profiles;
        Map<String, String> links;
        Map<TaskGroup, Set<User>> tasksgroups;
        DocumentModel[] models;
        ComplexModel[] cmodels;
        SearchHelpData[] shdata;
        Authorization[] authorizations;
        String[] dependencies;
        State state;
        
        /*
         * Registra instalação do pacote
         */
        state = new State();
        state.data = message.get("data");
        state.pkgname = message.getString("name");
        state.documents = new Documents(this);
        state.pkgid = state.documents.getNextNumber("PKGCODE") * 1000000;
        state.shm = new HashMap<String, Set<DocumentModelItem>>();
        state.function = this;
        
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
        
        header = new ExtendedObject(state.documents.getModel("PACKAGE"));
        header.setValue("NAME", state.pkgname);
        header.setValue("CODE", state.pkgid);
        state.documents.save(header);
        
        /*
         * insere usuários
         */
        users = state.data.getUsers();
        if (users.size() > 0)
            InstallUsers.init(users, state);
        
        /*
         * gera modelos;
         * insere registros;
         * prepara dados para ajuda de pesquisa.
         */
        models = state.data.getModels();
        if (models.length > 0)
            InstallModels.init(models, state);
        
        cmodels = state.data.getCModels();
        if (cmodels.length > 0)
            InstallCModels.init(cmodels, state);
        
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
    
        return 1;
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
     */
    public final void uninstall(Message message) {
        String modeltype, name;
        ExtendedObject object;
        SHLib shlib;
        Authority authority;
        Documents documents;
        String pkgname = message.getString("package");
        ExtendedObject[] objects = Registry.getEntries(pkgname, this);
        
        if (objects == null)
            return;
        
        authority = new Authority(this);
        shlib = new SHLib(this);
        documents = new Documents(this);
        for (int i = objects.length; i > 0; i--) {
            object = objects[i - 1];
            modeltype = object.getValue("MODEL");
            name = object.getValue("NAME");
            
            if (modeltype.equals("MESSAGE")) {
                name = object.getValue("PACKAGE");
                documents.update(QUERIES[DEL_MESSAGES], name);
                documents.update(QUERIES[DEL_PKG_ITEM], name, "MESSAGE");
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("SH")) {
                shlib.unassign(name);
                shlib.remove(name);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("TASK")) {
                documents.update(QUERIES[DEL_TASKS], name);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("MODEL")) {
                documents.removeModel(name);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("NUMBER")) {
                documents.removeNumberFactory(name);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("AUTHORIZATION")) {
                authority.remove(name);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("TSKGROUP")) {
                TaskSelector.removeGroup(name, documents);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("TSKITEM")) {
                TaskSelector.removeTask(name, documents);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("CMODEL")) {
                documents.removeComplexModel(name);
                documents.delete(object);
                
                continue;
            }
            
            if (modeltype.equals("DATA_ELEMENT"))
                documents.delete(object);
        }
            
        new Iocaste(this).invalidateAuthCache();
        documents.update(QUERIES[DEL_PACKAGE], pkgname);
        documents.commit();
    }
}

class State {
    public Documents documents;
    public long pkgid;
    public String pkgname;
    public Map<String, Set<DocumentModelItem>> shm;
    public InstallData data;
    public Function function;
    public Map<String, Map<String, String>> messages;
}
