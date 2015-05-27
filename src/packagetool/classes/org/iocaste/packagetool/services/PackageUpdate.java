package org.iocaste.packagetool.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;
import org.iocaste.shell.common.StyleSheet;

public class PackageUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        DocumentModel model;
        Uninstall uninstall;
        Set<String> types;
        String defaultstyle;
        Map<String, StyleSheet> stylesheets;
        Map<String, DocumentModel> models;
        Map<String, String> links;
        Map<TaskGroup, Set<User>> tasksgroups;
        Map<UserProfile, Set<User>> profiles;
        Authorization[] authorizations;
        ComplexModel[] cmodels;
        SearchHelpData[] shdata;
        DocumentModel tasks;
        State state;
        Services services;
        
        state = new State();
        state.data = message.get("data");
        state.function = getFunction();
        state.documents = new Documents(state.function);
        state.pkgname = message.getString("name");
        
        models = state.data.getModels();
        for (String name : models.keySet()) {
            model = models.get(name);
            if (state.documents.getModel(name) == null)
                Models.install(model, name, state);
            else
                Models.update(model, name, state);
        }
        
        types = new HashSet<>();
        types.add("SH");
        types.add("MESSAGE");
        types.add("STYLE");
        types.add("AUTHORIZATION");
        types.add("AUTH_PROFILE");
        types.add("TSKGROUP");
        types.add("TSKITEM");
        types.add("TASK");
        types.add("CMODEL");
        
        services = getFunction();
        uninstall = services.get("uninstall");
        uninstall.run(state.pkgname, types);
        
        shdata = state.data.getSHData();
        if (shdata.length > 0)
            InstallSH.init(shdata, state);
        
        cmodels = state.data.getCModels();
        if (cmodels.length > 0)
            InstallCModels.init(cmodels, state);
        
        state.messages = state.data.getMessages();
        if (state.messages.size() > 0)
            InstallMessages.init(state);
        
        defaultstyle = state.data.getApplicationStyle();
        if (defaultstyle != null)
            InstallStyles.setDefaultStyle(state, defaultstyle);
        
        stylesheets = state.data.getStyleSheets();
        if (stylesheets.size() > 0)
            InstallStyles.init(stylesheets, state);
        
        authorizations = state.data.getAuthorizations();
        if (authorizations.length > 0)
            InstallAuthorizations.init(authorizations, state);
        
        profiles = state.data.getUserProfiles();
        if (profiles.size() > 0)
            InstallAuthorizations.init(profiles, state);
        
        new Iocaste(state.function).invalidateAuthCache();

        links = state.data.getLinks();
        if (links.size() > 0) {
            tasks = state.documents.getModel("TASKS");
            InstallLinks.init(links, tasks, state);
        }
        
        tasksgroups = state.data.getTasksGroups();
        if (tasksgroups.size() > 0)
            InstallTasksGroups.init(tasksgroups, state);
        
        /*
         * grava itens instalados
         */
        for (ExtendedObject object : state.log)
            state.documents.save(object);
        
        return null;
    }

}
