package org.iocaste.packagetool.services;

import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.shell.common.SHLib;

public class InstallSH {
    
    /**
     * 
     * @param shdata
     * @param state
     */
    public static final void init(SearchHelpData[] shdata, State state) {
        Set<DocumentModelItem> shm;
        ExtendedObject header;
        String shname;
        String[] shitens;
        ExtendedObject[] itens;
        int i;
        SHLib shlib = new SHLib(state.function);
        DocumentModel shmodel = state.documents.getModel("SEARCH_HELP");
        DocumentModel shimodel = state.documents.getModel("SH_ITENS");
        
        for (SearchHelpData shd : shdata) {
            shname = shd.getName();
            header = new ExtendedObject(shmodel);
            header.set("NAME", shname);
            header.set("MODEL", shd.getModel());
            header.set("EXPORT", shd.getExport());
            
            shitens = shd.getItens();
            itens = new ExtendedObject[shitens.length];

            i = 0;
            for (String name : shitens) {
                itens[i] = new ExtendedObject(shimodel);
                itens[i].set("NAME", name);
                itens[i].set("SEARCH_HELP", shname);
                itens[i++].set("ITEM", name);
            }
            
            shlib.save(header, itens);
            shm = state.shm.get(shname);
            if (shm != null)
                for (DocumentModelItem modelitem : shm)
                    shlib.assign(modelitem);

            Registry.add(shname, "SH", state);
        }
    }

}
