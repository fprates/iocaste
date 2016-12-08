package org.iocaste.packagetool.services;

import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.shell.common.SHLib;

public class InstallSH {
    
    /**
     * 
     * @param shdata
     * @param state
     */
    public static final void init(SearchHelpData[] shdata, State state) {
        Set<DocumentModelItem> shm;
        String shname;
        SHLib shlib = new SHLib(state.function);
        
        for (SearchHelpData shd : shdata) {
            shlib.save(shd);
            shname = shd.getName();
            shm = state.shm.get(shname);
            if (shm != null)
                for (DocumentModelItem modelitem : shm)
                    shlib.assign(modelitem);

            Registry.add(shname, "SH", state);
        }
    }
    
    public static final void reassign(State state) {
        SHLib shlib = new SHLib(state.function);
        Map<String, DocumentModel> models = state.data.getModels();
        
        for (String name : models.keySet())
            for (DocumentModelItem item : models.get(name).getItens())
                shlib.assign(item);
    }

}
