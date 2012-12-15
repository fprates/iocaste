package org.iocaste.workbench;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.GlobalConfigData;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

    public static final InstallData init() {
        String repository;
        UserProfile profile;
        TaskGroup taskgroup;
        Map<String, String> messages;
        Authorization authorization;
        GlobalConfigData config;
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
        
        /*
         * modelos
         */
        Editor.install(data);
        ProjectModels.install(data);
        
        /*
         * configurações
         */
        config = new GlobalConfigData();
        repository = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).append(".iocaste").
                append(File.separator).append("workbench").toString();
        config.define("repository", String.class, repository);
        data.add(config);
        
        /*
         *  link
         */
        data.link("WORKBENCH", "iocaste-workbench");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("WORKBENCH");
        data.add(taskgroup);
        
        return data;
    }
}
