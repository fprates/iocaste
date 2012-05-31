package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.SHLib;

public class Services extends AbstractFunction {

    public Services() {
        export("install", "install");
        export("is_installed", "isInstalled");
        export("uninstall", "uninstall");
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
        Map<String, Map<String, String>> messages;
        DocumentModel[] models;
        SearchHelpData[] shdata;
        Authorization[] authorizations;
        State state = new State();
        
        /*
         * Registra instalação do pacote
         */
        state.pkgname = message.getString("name");
        state.data = message.get("data");
        state.documents = new Documents(this);
        state.pkgid = state.documents.getNextNumber("PKGCODE") * 1000000;
        state.shm = new HashMap<String, DocumentModelItem>();
        state.function = this;
        
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
        messages = state.data.getMessages();
        if (messages.size() > 0)
            installMessages(messages, state);
        
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
    }
    
    /**
     * 
     * @param authorizations
     * @param state
     */
    private final void installAuthorizations(Authorization[] authorizations,
            State state) throws Exception {
        String name;
        Authority authority = new Authority(state.function);
        
        for (Authorization authorization : authorizations) {
            name = authorization.getName();
            
            if (authority.get(name) == null)
                authority.save(authorization);
            
            authority.assign("ADMIN", "ALL", authorization);
            
            Registry.add(name, "AUTHORIZATION", state);
        }
    }
    
    /**
     * 
     * @param links
     * @param tasks
     * @param state
     * @throws Exception
     */
    private final void installLinks(Map<String, String> links,
            DocumentModel tasks, State state) throws Exception {
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
     * @param msgsource
     * @param state
     * @throws Exception
     */
    private final void installMessages(
            Map<String, Map<String, String>> msgsource, State state)
                    throws Exception {
        long index;
        int langcode;
        String locale;
        Map<String, String> messages;
        ExtendedObject[] languages = state.documents.select("from LANGUAGES");
        DocumentModel msgmodel = state.documents.getModel("MESSAGES");
        ExtendedObject omessage = new ExtendedObject(msgmodel);
        
        for (String language : msgsource.keySet()) {
            langcode = 0;
            for (ExtendedObject olanguage : languages) {
                locale = olanguage.getValue("LOCALE");
                if (language.equals(locale)) {
                    langcode = olanguage.getValue("CODE");
                    break;
                }
            }
            
            messages = msgsource.get(language);
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
            
            for (DocumentModelItem modelitem : model.getItens())
                if (modelitem.getSearchHelp() != null)
                    state.shm.put(modelitem.getSearchHelp(), modelitem);
            
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
     * @throws Exception
     */
    private final void installSH(SearchHelpData[] shdata, State state)
            throws Exception {
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
            
            if (state.shm.containsKey(shname))
                shlib.assign(state.shm.get(shname));

            Registry.add(shname, "SH", state);
        }
    }
    
    /**
     * 
     * @param tasksgroups
     * @param state
     * @throws Exception
     */
    private final void installTasksGroups(Map<String, Set<String>> tasksgroups,
            State state) throws Exception {
        Set<String> itens;
        ExtendedObject group;
        
        for (String groupname : tasksgroups.keySet()) {
            group = TaskSelector.getGroup(groupname, state);
            if (group == null) {
                group = TaskSelector.createGroup(groupname, state);
                TaskSelector.assignGroup(group, "ADMIN", state);
                Registry.add(groupname, "TSKGROUP", state);
            }

            itens = tasksgroups.get(groupname);
            for (String taskname : itens) {
                TaskSelector.addEntry(taskname, group, state);
                Registry.add(taskname, "TSKITEM", state);
            }
        }
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean isInstalled(Message message) throws Exception {
        String package_ = message.getString("package");
        ExtendedObject item = new Documents(this).
                getObject("PACKAGE", package_);
        
        return (item == null)? false : true;
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void uninstall(Message message) throws Exception {
        String query, modeltype, name;
        ExtendedObject object;
        SHLib shlib = new SHLib(this);
        Documents documents = new Documents(this);
        Authority authority = new Authority(this);
        String pkgname = message.getString("package");
        ExtendedObject[] objects = Registry.getEntries(pkgname, this);
        
        for (int i = objects.length; i > 0; i--) {
            object = objects[i - 1];
            
            modeltype = object.getValue("MODEL");
            name = object.getValue("NAME");
            if (modeltype.equals("MESSAGE")) {
                name = object.getValue("PACKAGE");
                query = "delete from MESSAGES where PACKAGE = ?";
                documents.update(query, name);
                
                query = "delete from PACKAGE_ITEM where PACKAGE = ? and " +
                		"MODEL = ?";
                documents.update(query, name, "MESSAGE");
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
                query = "delete from TASKS where NAME = ?";
                documents.update(query, name);
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
            
            if (modeltype.equals("DATA_ELEMENT"))
                documents.delete(object);
        }
        
        documents.update("delete from PACKAGE where NAME = ?", pkgname);
        documents.commit();
    }
}

class State {
    public Documents documents;
    public long pkgid;
    public String pkgname;
    public Map<String, DocumentModelItem> shm;
    public InstallData data;
    public Function function;
}
