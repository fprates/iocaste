package org.iocaste.packagetool.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class Models {
    
    private static final void extractsh(DocumentModel model, State state) {
        String name;
        Set<DocumentModelItem> itens;
        
        for (DocumentModelItem modelitem : model.getItens()) {
            name = modelitem.getSearchHelp();
            if (name == null)
                continue;
            
            if (state.shm.containsKey(name)) {
                itens = state.shm.get(name);
            } else {
                itens = new TreeSet<>();
                state.shm.put(name, itens);
            }
            itens.add(modelitem);
        }
    }
    
    public static final void installAll(Map<String, DocumentModel> models,
            State state) throws Exception {
        int error;
        List<DocumentModel> toinstall;
        
        toinstall = new ArrayList<>();
        for (String modelname : models.keySet()) {
            if (state.documents.getModel(modelname) != null) {
                Registry.add(modelname, "MODEL", state);
                continue;
            }
            
            toinstall.add(models.get(modelname));
        }
        
        if (toinstall.size() == 0)
            return;
        error = state.documents.
                createModels(toinstall.toArray(new DocumentModel[0]));
        if (error < 0)
            throw new IocasteException(
                    new StringBuilder("error creating models.").toString());
        for (DocumentModel installed : toinstall)
            install(installed, installed.getName(), state);
    }
    
    public static final void install(DocumentModel model, String modelname,
            State state) throws Exception {
        int i;
        List<Object[]> values;
        ExtendedObject header;
        
        model.setPackage(state.pkgname);
        Registry.add(modelname, "MODEL", state);
        extractsh(model, state);
        
        /*
         * recupera modelo para trazer as queries.
         */
        model = state.documents.getModel(model.getName());
        values = state.data.getValues(model);
        
        if (values == null)
            return;
        
        for (Object[] line : values) {
            header = new ExtendedObject(model);
            i = 0;
            
            for (DocumentModelItem modelitem : model.getItens())
                header.set(modelitem, line[i++]);
            
            state.documents.save(header);
        }
    }
    
    public static final void update(DocumentModel model, String modelname,
            State state) throws Exception {
        int i;
        List<Object[]> values;
        ExtendedObject header;
        
        if (state.documents.updateModel(model) < 0)
            throw new IocasteException("update model error.");
        
        extractsh(model, state);
        
        /*
         * recupera modelo para trazer as queries.
         */
        model = state.documents.getModel(model.getName());
        values = state.data.getValues(model);
        
        if (values == null)
            return;
        
        for (Object[] line : values) {
            header = new ExtendedObject(model);
            i = 0;
            
            for (DocumentModelItem modelitem : model.getItens())
                header.set(modelitem, line[i++]);
            
            state.documents.modify(header);
        }
    }
}
