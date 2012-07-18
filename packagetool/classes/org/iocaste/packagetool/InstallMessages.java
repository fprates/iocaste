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
        long index;
        int langcode;
        String locale;
        Map<String, String> messages;
        ExtendedObject[] languages = state.documents.select("from LANGUAGES");
        DocumentModel msgmodel = state.documents.getModel("MESSAGES");
        ExtendedObject omessage = new ExtendedObject(msgmodel);
        
        for (String language : state.messages.keySet()) {
            langcode = 0;
            for (ExtendedObject olanguage : languages) {
                locale = olanguage.getValue("LOCALE");
                if (language.equals(locale)) {
                    langcode = olanguage.getValue("CODE");
                    break;
                }
            }
            
            messages = state.messages.get(language);
            index = (langcode * 1000000000) + (state.pkgid / 100);
            for (String msgcode : messages.keySet()) {
                omessage.setValue("INDEX", index++);
                omessage.setValue("NAME", msgcode);
                omessage.setValue("LOCALE", language);
                omessage.setValue("PACKAGE", state.pkgname);
                omessage.setValue("TEXT", messages.get(msgcode));
                
                state.documents.save(omessage);
            }
        }
        
        Registry.add(null, "MESSAGE", state);
    }

}
