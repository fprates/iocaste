package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class InstallLinks {
    
    /**
     * 
     * @param links
     * @param tasks
     * @param state
     */
    public static final void init(Map<String, String> links,
            DocumentModel tasks, State state) {
        ExtendedObject header;
        
        for (String link : links.keySet()) {
            header = new ExtendedObject(tasks);
            header.set("NAME", link.toUpperCase());
            header.set("COMMAND", links.get(link));
            
            state.documents.save(header);
            Registry.add(link, "TASK", state);
        }
    }

}
