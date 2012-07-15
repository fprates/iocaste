package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
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
     */
    public final void assignTaskGroup(Message message) {
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
        Map<String, String> links;
        Map<String, Set<String>> tasksgroups;
        DocumentModel[] models;
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
        
        try {
            dependencies = state.data.getDependencies();
            if (dependencies != null)
                for (String pkgname : dependencies) {
                    if (!isInstalled(pkgname))
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
             * gera modelos;
             * insere registros;
             * prepara dados para ajuda de pesquisa.
             */
            models = state.data.getModels();
            if (models.length > 0)
                installModels(models, state);
            
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
                installSH(shdata, state);
            
            for (DataElement element : state.data.getElements())
                Registry.add(element.getName(), "DATA_ELEMENT", state);
            
            /*
             * registra mensagens
             */
            state.messages = state.data.getMessages();
            if (state.messages.size() > 0)
                installMessages(state);
            
            authorizations = state.data.getAuthorizations();
            if (authorizations.length > 0)
                installAuthorizations(authorizations, state);
            
            /*
             * registra tarefas
             */
            tasks = state.documents.getModel("TASKS");
            links = state.data.getLinks();
            if (links.size() > 0)
                installLinks(links, tasks, state);
            
            tasksgroups = state.data.getTasksGroups();
            if (tasksgroups.size() > 0)
                installTasksGroups(tasksgroups, state);
            
            state.documents.commit();
        
            return 1;
        } catch (Exception e) {
            state.documents.rollback();
            throw e;
        }
    }
    
    /**
     * 
     * @param authorizations
     * @param state
     */
    private final void installAuthorizations(Authorization[] authorizations,
            State state) {
        String name;
        Authority authority = new Authority(state.function);
        
        for (Authorization authorization : authorizations) {
            name = authorization.getName();
            if (authority.get(name) == null)
                authority.save(authorization);
            
            authority.assign("ADMIN", "ALL", authorization);
            Registry.add(name, "AUTHORIZATION", state);
        }
        
        new Iocaste(state.function).invalidateAuthCache();
    }
    
    /**
     * 
     * @param links
     * @param tasks
     * @param state
     */
    private final void installLinks(Map<String, String> links,
            DocumentModel tasks, State state) {
        ExtendedObject header;
        
        for (String link : links.keySet()) {
            header = new ExtendedObject(tasks);
            header.setValue("NAME", link.toUpperCase());
            header.setValue("COMMAND", links.get(link));
            
            state.documents.save(header);
            Registry.add(link, "TASK", state);
        }
    }
    
    /**
     * 
     * @param state
     */
    private final void installMessages(State state) {
        long index;
        int langcode;
        String locale;
        Map<String, String> messages;
        ExtendedObject[] languages = state.documents.select("from LANGUAGES");
        DocumentModel msgmodel = state.documents.getModel("MESSAGES");
        ExtendedObject omessage = new ExtendedObject(msgmodel);
        
        for (String language : state.messages.keySet()) {
            langcode = 0;
            for (ExtendedObject olanguage : languages) {
                locale = olanguage.getValue("LOCALE");
                if (language.equals(locale)) {
                    langcode = olanguage.getValue("CODE");
                    break;
                }
            }
            
            messages = state.messages.get(language);
            index = (langcode * 1000000000) + (state.pkgid / 100);
            for (String msgcode : messages.keySet()) {
                omessage.setValue("INDEX", index++);
                omessage.setValue("NAME", msgcode);
                omessage.setValue("LOCALE", language);
                omessage.setValue("PACKAGE", state.pkgname);
                omessage.setValue("TEXT", messages.get(msgcode));
                
                state.documents.save(omessage);
            }
        }
        
        Registry.add(null, "MESSAGE", state);
    }
    
    /**
     * 
     * @param models
     * @param state
     * @throws Exception
     */
    private final void installModels(DocumentModel[] models, State state)
            throws Exception {
        String name;
        Set<DocumentModelItem> itens;
        int i;
        List<Object[]> values;
        ExtendedObject header;
        
        for (DocumentModel model : models) {
            if (state.documents.getModel(model.getName()) != null) {
                if (state.documents.updateModel(model) == 0)
                    throw new IocasteException("update model error.");
            } else {
                if (state.documents.createModel(model) == 0)
                    throw new IocasteException("create model error.");
            }
            
            Registry.add(model.getName(), "MODEL", state);
            
            for (DocumentModelItem modelitem : model.getItens()) {
                name = modelitem.getSearchHelp();
                if (name == null)
                    continue;
                
                if (state.shm.containsKey(name)) {
                    itens = state.shm.get(name);
                } else {
                    itens = new TreeSet<DocumentModelItem>();
                    state.shm.put(name, itens);
                }
                itens.add(modelitem);
            }
            
            /*
             * recupera modelo para trazer as queries.
             */
            model = state.documents.getModel(model.getName());
            values = state.data.getValues(model);
            
            if (values == null)
                continue;
            
            for (Object[] line : values) {
                header = new ExtendedObject(model);
                i = 0;
                
                for (DocumentModelItem modelitem : model.getItens())
                    header.setValue(modelitem, line[i++]);
                
                state.documents.save(header);
            }
        }
    }
    
    /**
     * 
     * @param shdata
     * @param state
     */
    private final void installSH(SearchHelpData[] shdata, State state) {
        Set<DocumentModelItem> shm;
        ExtendedObject header;
        String shname;
        String[] shitens;
        ExtendedObject[] itens;
        int i;
        SHLib shlib = new SHLib(this);
        DocumentModel shmodel = state.documents.getModel("SEARCH_HELP");
        DocumentModel shimodel = state.documents.getModel("SH_ITENS");
        
        for (SearchHelpData shd : shdata) {
            shname = shd.getName();
            header = new ExtendedObject(shmodel);
            header.setValue("NAME", shname);
            header.setValue("MODEL", shd.getModel());
            header.setValue("EXPORT", shd.getExport());
            
            shitens = shd.getItens();
            itens = new ExtendedObject[shitens.length];

            i = 0;
            for (String name : shitens) {
                itens[i] = new ExtendedObject(shimodel);
                itens[i].setValue("NAME", name);
                itens[i].setValue("SEARCH_HELP", shname);
                itens[i++].setValue("ITEM", name);
            }
            
            shlib.save(header, itens);
            shm = state.shm.get(shname);
            if (shm != null)
                for (DocumentModelItem modelitem : shm)
                    shlib.assign(modelitem);

            Registry.add(shname, "SH", state);
        }
    }
    
    /**
     * 
     * @param tasksgroups
     * @param state
     */
    private final void installTasksGroups(Map<String, Set<String>> tasksgroups,
            State state) {
        Set<String> itens;
        ExtendedObject task, group;
        
        for (String groupname : tasksgroups.keySet()) {
            group = TaskSelector.getGroup(groupname, state);
            if (group == null) {
                group = TaskSelector.createGroup(groupname, state);
                TaskSelector.assignGroup(group, "ADMIN", state);
                Registry.add(groupname, "TSKGROUP", state);
            }

            itens = tasksgroups.get(groupname);
            for (String taskname : itens) {
                task = TaskSelector.addEntry(taskname, group, state);
                if (state.messages.size() > 0)
                    TaskSelector.addEntryMessage(task, group, state);
                
                Registry.add(taskname, "TSKITEM", state);
            }
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
