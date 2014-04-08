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
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.user.Authorization;

public class Install {
    
    /**
     * 
     * @param function
     * @return
     */
    public static final InstallData init(Function function) {
        TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        Models models = new Models();

        installLanguages(data, models);
        installTasks(data, models);
        installMessages(data, models, function);
        installTasksGroups(data, models);
        installUserTasksGroups(data, models, function);
        
        messages = new HashMap<>();
        messages.put("package.installed", "Pacote instalado com sucesso.");
        messages.put("package.uninstalled", "Pacote desinstalado com sucesso.");
        messages.put("package-manager", "Gerenciador de pacotes");
        messages.put("packageinstall", "Instalar");
        messages.put("packageuninstall", "Desinstalar");
        messages.put("PACKAGE", "Gerenciador de pacotes");
        messages.put("name", "Nome");
        messages.put("action", "Ação");
        messages.put("ADMIN", "Administração");
        messages.put("info", "Conteúdo do pacote");
        messages.put("package-contents", "Conteúdo do pacote");
        data.setMessages("pt_BR", messages);
        
        data.link("PACKAGE", "iocaste-packagetool");
        taskgroup = new TaskGroup("ADMIN");
        taskgroup.add("PACKAGE");
        data.add(taskgroup);
        
        authorization = new Authorization("PACKAGE.CALL");
        authorization.setAction("CALL");
        authorization.setObject("LINK");
        authorization.add("LINK", "PACKAGE");
        data.add(authorization);
        
        return data;
    }

    /**
     * 
     * @param data
     * @return
     */
    private static final void installLanguages(InstallData data, Models models)
    {
        int i;
        String tag, country;
        DocumentModelItem item;
        DocumentModel model = data.getModel("LANGUAGES", "LANG", null);
        DataElement element = new DataElement("LANGUAGES.LOCALE");
        
        element.setType(DataType.CHAR);
        element.setLength(5);
        element.setUpcase(false);
        
        item = new DocumentModelItem("LOCALE");
        item.setDataElement(element);
        item.setTableFieldName("LOCAL");
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DataElement("LANGUAGES.CODE");
        element.setType(DataType.NUMC);
        element.setLength(3);
        
        item = new DocumentModelItem("CODE");
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
        
        models.languages = model;
    }
    
    /**
     * 
     * @param data
     * @param models
     * @param function
     */
    private static final void installMessages(InstallData data, Models models,
            Function function) {
        DataElement element;
        Documents documents;
        DocumentModelItem item, reference;
        DocumentModel model = data.getModel("MESSAGES", "MSGSRC", null);

        
        /*
         * índice
         */
        element = new DataElement("INDEX");
        element.setType(DataType.NUMC);
        element.setLength(13);
        
        item = new DocumentModelItem("INDEX");
        item.setDataElement(element);
        item.setTableFieldName("MSGNR");
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        /*
         * nome
         */
        element = new DataElement("MESSAGES.NAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        element.setUpcase(false);
        
        item = new DocumentModelItem("NAME");
        item.setDataElement(element);
        item.setTableFieldName("MSGNM");
        
        model.add(item);
        
        /*
         * localização
         */
        reference = models.languages.getModelItem("LOCALE");
        element = reference.getDataElement();
        
        item = new DocumentModelItem("LOCALE");
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
        
        item = new DocumentModelItem("PACKAGE");
        item.setDataElement(element);
        item.setTableFieldName("PKGNM");
        item.setReference(reference);
        
        model.add(item);
        
        /*
         * mensagem
         */
        element = new DataElement("MESSAGES.TEXT");
        element.setType(DataType.CHAR);
        element.setLength(255);
        element.setUpcase(false);
        
        item = new DocumentModelItem("TEXT");
        item.setDataElement(element);
        item.setTableFieldName("MSGTX");
        
        model.add(item);
        
        models.messages = model;
    }
    
    /**
     * 
     * @param data
     */
    private static final void installTasks(InstallData data, Models models) {
        DataElement element;
        DocumentModelItem item;
        
        models.tasks = data.getModel("TASKS", "TASKS", null);

        element = new DataElement("TASKS.NAME");
        element.setLength(18);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("TSKNM");
        item.setDataElement(element);
        
        models.tasks.add(item);
        models.tasks.add(new DocumentModelKey(item));
        
        element = new DataElement("TASKS.COMMAND");
        element.setLength(128);
        element.setType(DataType.CHAR);
        
        item = new DocumentModelItem("COMMAND");
        item.setTableFieldName("CMDLN");
        item.setDataElement(element);
        
        models.tasks.add(item);
    }
    
    /**
     * 
     * @param data
     * @param models
     * @return
     */
    private static final void installTasksGroups(InstallData data,
            Models models) {
        DataElement element;
        DocumentModel model, group;
        DocumentModelItem item, groupname, entryid, language, text, reference;
        
        /*
         * grupos de tarefas
         */
        group = data.getModel("TASKS_GROUPS", "TASKSGRP", null);
        
        // nome do grupo
        element = new DataElement("TASKS_GROUPS.NAME");
        element.setType(DataType.CHAR);
        element.setLength(12);
        element.setUpcase(true);
        
        groupname = new DocumentModelItem("NAME");
        groupname.setTableFieldName("GRPID");
        groupname.setDataElement(element);
        group.add(groupname);
        group.add(new DocumentModelKey(groupname));
        
        // índice do grupo
        element = new DataElement("TASKS_GROUPS.ID");
        element.setType(DataType.NUMC);
        element.setLength(3);
        
        item = new DocumentModelItem("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        group.add(item);
        
        // último item do grupo
        element = new DataElement("TASKS_GROUPS.CURRENT");
        element.setType(DataType.NUMC);
        element.setLength(6);
        
        item = new DocumentModelItem("CURRENT");
        item.setTableFieldName("CRRNT");
        item.setDataElement(element);
        group.add(item);
        
        data.addNumberFactory("TSKGROUP");
        
        /*
         * item do grupo de tarefas
         */
        model = data.getModel("TASK_ENTRY", "TASKENTRY", null);
        
        // identificador
        element = new DataElement("TASK_ENTRY.ID");
        element.setType(DataType.NUMC);
        element.setLength(8);
        
        entryid = new DocumentModelItem("ID");
        entryid.setTableFieldName("IDENT");
        entryid.setDataElement(element);
        model.add(entryid);
        model.add(new DocumentModelKey(entryid));
        
        // nome da entrada
        reference = models.tasks.getModelItem("NAME");
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("ENTRY");
        item.setDataElement(reference.getDataElement());
        item.setReference(reference);
        model.add(item);
        
        // grupo
        element = groupname.getDataElement();
        
        item = new DocumentModelItem("GROUP");
        item.setTableFieldName("GRPID");
        item.setDataElement(element);
        item.setReference(groupname);
        model.add(item);
        
        
        /*
         * textos
         */
        model = data.getModel("TASK_ENTRY_TEXT", "TASKENTRYTXT", null);

        // identificador
        element = new DataElement("TASK_ENTRY_TEXT.ID");
        element.setType(DataType.NUMC);
        element.setLength(10);
        
        item = new DocumentModelItem("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));

        // tarefa do grupo
        element = entryid.getDataElement();
        
        item = new DocumentModelItem("TASK");
        item.setTableFieldName("ENTRY");
        item.setDataElement(element);
        item.setReference(entryid);
        model.add(item);
        
        // localização
        language = models.languages.getModelItem("LOCALE");
        element = language.getDataElement();
        
        item = new DocumentModelItem("LANGUAGE");
        item.setTableFieldName("LANGU");
        item.setDataElement(element);
        item.setReference(language);
        model.add(item);
        
        // texto
        text = models.messages.getModelItem("TEXT");
        element = text.getDataElement();
        
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("TEXT");
        item.setDataElement(element);
        model.add(item);
        
        models.group = group;
    }
    
    /**
     * 
     * @param data
     * @param models
     * @param function
     */
    private static final void installUserTasksGroups(InstallData data,
            Models models, Function function) {
        DataElement element;
        DocumentModel model;
        DocumentModelItem item, username, groupname;
        
        model = data.getModel("USER_TASKS_GROUPS", "USRTASKGRP", null);
        
        // identificador
        element = new DataElement("USER_TASKS_GROUPS.ID");
        element.setType(DataType.NUMC);
        element.setLength(8);
        
        item = new DocumentModelItem("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // usuário
        username = new Documents(function).getModel("LOGIN").
                getModelItem("USERNAME");
        element = username.getDataElement();
        
        item = new DocumentModelItem("USERNAME");
        item.setTableFieldName("UNAME");
        item.setDataElement(element);
        item.setReference(username);
        model.add(item);
        
        // grupo
        groupname = models.group.getModelItem("NAME");
        element = groupname.getDataElement();
        
        item = new DocumentModelItem("GROUP");
        item.setTableFieldName("GRPID");
        item.setDataElement(element);
        item.setReference(groupname);
        model.add(item);
    }
}

class Models {
    public DocumentModel languages, messages, group, tasks;
}