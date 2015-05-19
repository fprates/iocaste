package org.iocaste.packagetool.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
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
        types.add("MESSAGE");
        types.add("STYLE");
        
        services = getFunction();
        uninstall = services.get("uninstall");
        uninstall.run(state.pkgname, types);
        
        state.messages = state.data.getMessages();
        if (state.messages.size() > 0)
            InstallMessages.init(state);
        
        defaultstyle = state.data.getApplicationStyle();
        if (defaultstyle != null)
            InstallStyles.setDefaultStyle(state, defaultstyle);
        
        stylesheets = state.data.getStyleSheets();
        if (stylesheets.size() > 0)
            InstallStyles.init(stylesheets, state);
        
        return null;
    }

}
