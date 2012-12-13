package org.iocaste.workbench;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.GlobalConfigData;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

    public static final InstallData init() {
        DataElement element;
        String repository;
        UserProfile profile;
        TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        GlobalConfigData config;
        DocumentModel model;
        DocumentModelItem item;
        InstallData data = new InstallData();
        
        // mensagens
        messages = new HashMap<String, String>();
        messages.put("iocaste-workbench", "Workbench");
        messages.put("WORKBENCH", "Workbench");
        data.setMessages("pt_BR", messages);
        
        // autorização de execução
        authorization = new Authorization("WORKBENCH.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-workbench");
        data.add(authorization);
        
        profile = new UserProfile("DEVELOPER");
        profile.add(authorization);
        data.add(profile);
        
        // modelo de projeto
        model = data.getModel("PROJECT_HEADER", null, null);
        
        element = new DummyElement("PACKAGE.NAME");
        item = new DocumentModelItem("NAME");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("PROJECT_HEADER.PACKAGE");
        element.setType(DataType.CHAR);
        element.setLength(128);
        element.setUpcase(false);
        item = new DocumentModelItem("PACKAGE");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("PROJECT_HEADER.CLASS");
        element.setType(DataType.CHAR);
        element.setLength(64);
        element.setUpcase(false);
        item = new DocumentModelItem("CLASS");
        item.setDataElement(element);
        model.add(item);
        
        // configurações
        config = new GlobalConfigData();
        repository = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).append(".iocaste").
                append(File.separator).append("workbench").toString();
        config.define("repository", String.class, repository);
        data.add(config);
        
        // link
        data.link("WORKBENCH", "iocaste-workbench");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("WORKBENCH");
        data.add(taskgroup);
        
        return data;
    }
}
