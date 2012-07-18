package org.iocaste.packagetool;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class InstallModels {
    public static final void init(DocumentModel[] models, State state)
            throws Exception {
        String name;
        Set<DocumentModelItem> itens;
        int i;
        List<Object[]> values;
        ExtendedObject header;
        
        for (DocumentModel model : models) {
            if (state.documents.getModel(model.getName()) != null) {
                if (state.documents.updateModel(model) == 0)
                    throw new IocasteException("update model error.");
            } else {
                if (state.documents.createModel(model) == 0)
                    throw new IocasteException("create model error.");
            }
            
            Registry.add(model.getName(), "MODEL", state);
            
            for (DocumentModelItem modelitem : model.getItens()) {
                name = modelitem.getSearchHelp();
                if (name == null)
                    continue;
                
                if (state.shm.containsKey(name)) {
                    itens = state.shm.get(name);
                } else {
                    itens = new TreeSet<DocumentModelItem>();
                    state.shm.put(name, itens);
                }
                itens.add(modelitem);
            }
            
            /*
             * recupera modelo para trazer as queries.
             */
            model = state.documents.getModel(model.getName());
            values = state.data.getValues(model);
            
            if (values == null)
                continue;
            
            for (Object[] line : values) {
                header = new ExtendedObject(model);
                i = 0;
                
                for (DocumentModelItem modelitem : model.getItens())
                    header.setValue(modelitem, line[i++]);
                
                state.documents.save(header);
            }
        }
    }

}
