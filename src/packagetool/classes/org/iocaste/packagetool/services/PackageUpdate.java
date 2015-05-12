package org.iocaste.packagetool.services;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class PackageUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
//        Map<String, DocumentModel> models;
        State state;
        
        state = new State();
        state.data = message.get("data");
        state.function = getFunction();
        state.documents = new Documents(state.function);
        state.pkgname = message.getString("name");
        
//        models = state.data.getModels();
//        if (models != null)
//            Models.update(models, state);
        state.messages = state.data.getMessages();
        if (state.messages.size() > 0) {
            InstallMessages.uninstall(state.pkgname, state.documents);
            InstallMessages.init(state);
        }
        return null;
    }

}
