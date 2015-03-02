package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class InstallMessages {
    
    /**
     * 
     * @param state
     */
    public static final void init(State state) {
        String index;
        Map<String, String> messages;
        int i;
        DocumentModel msgmodel = state.documents.getModel("MESSAGES");
        ExtendedObject omessage = new ExtendedObject(msgmodel);
        
        for (String language : state.messages.keySet()) {
            i = 0;
            messages = state.messages.get(language);
            for (String msgcode : messages.keySet()) {
                index = String.format(
                        "%s%05d_%s", state.pkgname, i++, language);
                omessage.set("INDEX", index);
                omessage.set("NAME", msgcode);
                omessage.set("LOCALE", language);
                omessage.set("PACKAGE", state.pkgname);
                omessage.set("TEXT", messages.get(msgcode));
                state.documents.save(omessage);
            }
        }
        
        Registry.add(null, "MESSAGE", state);
    }

}
