package org.iocaste.packagetool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
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
        Map<String, Map<String, String>> messages;
        DocumentModel[] models;
        SearchHelpData[] shdata;
        State state = new State();
        
        /*
         * Registra instalação do pacote
         */
        state.pkgname = message.getString("name");
        state.data = message.get("data");
        state.documents = new Documents(this);
        state.pkgid = state.documents.getNextNumber("PKGCODE") * 1000000;
        state.shm = new HashMap<String, DocumentModelItem>();
        
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
         * registra tarefas
         */
        tasks = state.documents.getModel("TASKS");
        links = state.data.getLinks();
        if (links.size() > 0)
            installLinks(links, tasks, state);
        
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
        
        messages = state.data.getMessages();
        if (messages.size() > 0)
            installMessages(messages, state);
        
        state.documents.commit();
        
        return 1;
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
            header.setValue("NAME", link);
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
        String model;
//        SHLib shlib;
        String pkgname = message.getString("package");
        ExtendedObject[] objects = Registry.getEntries(pkgname, this);
        List<ExtendedObject> shs = new ArrayList<ExtendedObject>();
        List<ExtendedObject> elements = new ArrayList<ExtendedObject>();
        
        for (ExtendedObject object : objects) {
            model = object.getValue("MODEL");
            
            if (model.equals("SEARCH_HELP")) {
                shs.add(object);
                continue;
            }
            
            if (model.equals("DATA_ELEMENT")) {
                elements.add(object);
                continue;
            }
        }
//        
//        shlib = new SHLib(this);
//        for (ExtendedObject object : shs) {
//            name = object.getValue("NAME");
//        }
    }
}

class State {
    public Documents documents;
    public long pkgid;
    public String pkgname;
    public Map<String, DocumentModelItem> shm;
    public InstallData data;
}
