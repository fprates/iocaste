package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;

public class InstallMessages {
    
    /**
     * 
     * @param state
     */
    public static final void init(State state) {
        Query query;
        long index;
        int langcode;
        String locale;
        Map<String, String> messages;
        ExtendedObject[] languages;
        DocumentModel msgmodel = state.documents.getModel("MESSAGES");
        ExtendedObject omessage = new ExtendedObject(msgmodel);

        query = new Query();
        query.setModel("LANGUAGES");
        languages = state.documents.select(query);
        for (String language : state.messages.keySet()) {
            langcode = 0;
            for (ExtendedObject olanguage : languages) {
                locale = olanguage.get("LOCALE");
                if (language.equals(locale)) {
                    langcode = olanguage.geti("CODE");
                    break;
                }
            }
            
            messages = state.messages.get(language);
            index = (langcode * 10000000) + (state.pkgid / 100);
            for (String msgcode : messages.keySet()) {
                omessage.set("INDEX", index++);
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