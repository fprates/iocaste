package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.DummyModelItem;

public class TasksGroupsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement taskgroupname, taskentryid, entrytextid, text;
        ModelInstall model;
        DocumentModelItem groupname;

        taskgroupname = elementchar(
                "TASKS_GROUPS.NAME", 12, DataType.UPPERCASE);
        taskentryid = elementchar(
                "TASK_ENTRY.ID", 15, DataType.UPPERCASE);
        entrytextid = elementchar(
                "TASK_ENTRY_TEXT", 18, DataType.UPPERCASE);
        text = elementchar(
                "TASK_TEXT", 64, DataType.KEEPCASE);
        
        /*
         * grupos de tarefas
         */
        model = modelInstance("TASKS_GROUPS", "TASKSGRP");
        groupname = tag("groupid", model.key(
                "NAME", "GRPID", taskgroupname));
        
        /*
         * item do grupo de tarefas
         */
        model = modelInstance("TASK_ENTRY", "TASKENTRY");
        model.key(
                "ID", "IDENT", taskentryid);
        model.reference(
                "GROUP", "GRPID", groupname);
        model.item(
                "NAME", "ENTRY", getItem("taskname"));
        
        /*
         * textos
         */
        model = modelInstance("TASK_ENTRY_TEXT", "TASKENTRYTXT");
        model.key(
                "ID", "TXTID", entrytextid);
        model.reference(
                "GROUP", "GRPID", groupname);
        model.reference(
                "LANGUAGE", "LANGU", new DummyElement("LANGUAGES.LOCALE"),
                    new DummyModelItem("LANGUAGES", "LOCALE"));
        model.item(
                "ENTRY", "ENTRY", taskentryid);
        model.item(
                "TEXT", "TEXT", text);
    }

}
