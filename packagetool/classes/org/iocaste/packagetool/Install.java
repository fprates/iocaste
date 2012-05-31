package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class Install {
    
    /**
     * 
     * @param function
     * @return
     * @throws Exception
     */
    public static final InstallData init(Function function) throws Exception {
        DocumentModel group;
        Map<String, String> messages;
        InstallData data = new InstallData();
        DocumentModel languages = installLanguages(data);
        
        installTasks(data);
        installMessages(data, languages, function);
        group = installTasksGroups(data);
        installUserTasksGroups(data, group, function);
        
        messages = new HashMap<String, String>();
        messages.put("package-manager", "Gerenciador de pacotes");
        
        data.setMessages("pt_BR", messages);
        data.link("PACKAGE", "iocaste-packagetool");
        data.addTaskGroup("ADMIN", "PACKAGE");
        
        return data;
    }

    /**
     * 
     * @param data
     * @return
     */
    private static final DocumentModel installLanguages(InstallData data) {
        int i;
        String tag, country;
        DocumentModelItem item;
        DocumentModel model = data.getModel("LANGUAGES", "LANG", "");
        DataElement element = new DataElement();
        
        element.setName("LANGUAGES.LOCALE");
        element.setType(DataType.CHAR);
        element.setLength(5);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("LOCALE");
        item.setDataElement(element);
        item.setTableFieldName("LOCAL");
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("LANGUAGES.CODE");
        element.setType(DataType.NUMC);
        element.setLength(3);
        
        item = new DocumentModelItem();
        item.setName("CODE");
        item.setDataElement(element);
        item.setTableFieldName("LCODE");
        
        model.add(item);
        
        i = 1;
        for (Locale locale : Locale.getAvailableLocales()) {
            country = locale.getCountry();
            if (country.length() == 0)
                continue;
            
            tag = new StringBuilder(locale.getLanguage()).append("_").
                    append(country).toString();
            data.addValues(model, tag, i++);
        }
        
        return model;
    }
    
    /**
     * 
     * @param data
     * @param languages
     * @param function
     * @throws Exception
     */
    private static final void installMessages(InstallData data,
            DocumentModel languages, Function function) throws Exception {
        Documents documents;
        DocumentModelItem item, reference;
        DocumentModel model = data.getModel("MESSAGES", "MSGSRC", "");
        DataElement element = new DataElement();
        
        /*
         * índice
         */
        element.setName("INDEX");
        element.setType(DataType.NUMC);
        element.setLength(13);
        
        item = new DocumentModelItem();
        item.setName("INDEX");
        item.setDataElement(element);
        item.setTableFieldName("MSGNR");
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        /*
         * nome
         */
        element = new DataElement();
        element.setName("MESSAGES.NAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("NAME");
        item.setDataElement(element);
        item.setTableFieldName("MSGNM");
        
        model.add(item);
        
        /*
         * localização
         */
        reference = languages.getModelItem("LOCALE");
        element = reference.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("LOCALE");
        item.setDataElement(element);
        item.setTableFieldName("LOCAL");
        item.setReference(reference);
        
        model.add(item);
        
        /*
         * pacote
         */
        documents = new Documents(function);
        reference = documents.getModel("PACKAGE").getModelItem("NAME"); 
        element = reference.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("PACKAGE");
        item.setDataElement(element);
        item.setTableFieldName("PKGNM");
        item.setReference(reference);
        
        model.add(item);
        
        /*
         * mensagem
         */
        element = new DataElement();
        element.setName("MESSAGES.TEXT");
        element.setType(DataType.CHAR);
        element.setLength(255);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("TEXT");
        item.setDataElement(element);
        item.setTableFieldName("MSGTX");
        
        model.add(item);
    }
    
    private static final void installTasks(InstallData data) {
        DataElement element;
        DocumentModel tasks;
        DocumentModelItem item;
        
        tasks = data.getModel("TASKS", "TASKS", "");

        element = new DataElement();
        element.setName("TASKS.NAME");
        element.setLength(18);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("NAME");
        item.setTableFieldName("TSKNM");
        item.setDataElement(element);
        
        tasks.add(item);
        tasks.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("TASKS.COMMAND");
        element.setLength(128);
        element.setType(DataType.CHAR);
        
        item = new DocumentModelItem();
        item.setName("COMMAND");
        item.setTableFieldName("CMDLN");
        item.setDataElement(element);
        
        tasks.add(item);
    }
    
    private static final DocumentModel installTasksGroups(InstallData data) {
        DataElement element;
        DocumentModel model, group;
        DocumentModelItem item, groupname;
        
        /*
         * grupos de tarefas
         */
        group = data.getModel("TASKS_GROUPS", "TASKSGRP", "");
        
        // nome do grupo
        element = new DataElement();
        element.setName("TASKS_GROUPS.NAME");
        element.setType(DataType.CHAR);
        element.setLength(12);
        element.setUpcase(true);
        
        groupname = new DocumentModelItem();
        groupname.setName("NAME");
        groupname.setTableFieldName("GRPID");
        groupname.setDataElement(element);
        group.add(groupname);
        group.add(new DocumentModelKey(groupname));
        
        // índice do grupo
        element = new DataElement();
        element.setName("TASKS_GROUPS.ID");
        element.setType(DataType.NUMC);
        element.setLength(3);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("INDEX");
        item.setDataElement(element);
        group.add(item);
        
        // contador de associações
        element = new DataElement();
        element.setName("TASKS_GROUPS.CURRENT");
        element.setType(DataType.NUMC);
        element.setLength(5);
        
        item = new DocumentModelItem();
        item.setName("CURRENT");
        item.setTableFieldName("CRRNT");
        item.setDataElement(element);
        group.add(item);
        
        // último item do grupo
        element = new DataElement();
        element.setName("TASKS_GROUPS.CURRENT_TASK");
        element.setType(DataType.NUMC);
        element.setLength(8);
        
        item = new DocumentModelItem();
        item.setName("CURRENT_TASK");
        item.setTableFieldName("CRTSK");
        item.setDataElement(element);
        group.add(item);
        
        data.addNumberFactory("TSKGROUP");
        /*
         * item do grupo de tarefas
         */
        model = data.getModel("TASK_ENTRY", "TASKENTRY", null);
        
        // identificador
        element = new DataElement();
        element.setName("TASK_ENTRY.ID");
        element.setType(DataType.NUMC);
        element.setLength(8);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // nome da entrada
        element = new DataElement();
        element.setName("TASK_ENTRY.NAME");
        element.setType(DataType.CHAR);
        element.setLength(12);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("NAME");
        item.setTableFieldName("ENTRY");
        item.setDataElement(element);
        model.add(item);
        
        // grupo
        element = groupname.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("GROUP");
        item.setTableFieldName("GRPID");
        item.setDataElement(element);
        item.setReference(groupname);
        model.add(item);
        
//        
//        /*
//         * textos
//         */
//        model = data.getModel("TASK_ENTRY_TEXT", "TASKENTRYTXT", null);
//        
//        element = new DataElement();
//        element.setName("TASKS_GROUPS.NAME");
//        element.setType(DataType.CHAR);
//        element.setLength(12);
//        element.setUpcase(true);
//        
//        item = new DocumentModelItem();
//        item.setTableFieldName("ID");
//        item.setDataElement(element);
//        model.add(item);
//        model.add(new DocumentModelKey(item));
        
        return group;
    }
    
    private static final void installUserTasksGroups(InstallData data,
            DocumentModel group, Function function) throws Exception {
        DataElement element;
        DocumentModel model;
        DocumentModelItem item, username, groupname;
        
        model = data.getModel("USER_TASKS_GROUPS", "USRTASKGRP", null);
        
        // identificador
        element = new DataElement();
        element.setName("USER_TASKS_GROUPS.ID");
        element.setType(DataType.NUMC);
        element.setLength(8);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // usuário
        username = new Documents(function).getModel("LOGIN").
                getModelItem("USERNAME");
        element = username.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("USERNAME");
        item.setTableFieldName("UNAME");
        item.setDataElement(element);
        item.setReference(username);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // grupo
        groupname = group.getModelItem("NAME");
        element = groupname.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("GROUP");
        item.setTableFieldName("GRPID");
        item.setDataElement(element);
        item.setReference(groupname);
        model.add(item);
        
//        // id entrada
//        element = new DataElement();
//        element.setName("TASKS_GROUPS.NAME");
//        element.setType(DataType.CHAR);
//        element.setLength(12);
//        element.setUpcase(true);
//        
//        item = new DocumentModelItem();
//        item.setName("NAME");
//        item.setTableFieldName("ENTRY");
//        item.setDataElement(element);
//        model.add(item);
//        
//        // texto
//        element = new DataElement();
//        element.setName("TASKS_GROUPS.NAME");
//        element.setType(DataType.CHAR);
//        element.setLength(12);
//        element.setUpcase(true);
//        
//        item = new DocumentModelItem();
//        item.setName("TEXT");
//        item.setTableFieldName("TEXT");
//        item.setDataElement(element);
//        model.add(item);
    }
}
