package org.iocaste.tasksel;

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
     * @return
     */
    public static final InstallData init(Function function) throws Exception {
        DocumentModel group;
        InstallData data = new InstallData();
        
        installTasks(data);
        group = installTasksGroups(data);
        installUserTasksGroups(data, group, function);
        
        return data;
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
        element.setName("TASKS_GROUPS.INDEX");
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

