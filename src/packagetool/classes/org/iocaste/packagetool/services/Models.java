package org.iocaste.packagetool.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class Models {
    public static final void install(Map<String, DocumentModel> models,
            State state) throws Exception {
        DocumentModel model;
        String name;
        Set<DocumentModelItem> itens;
        int i;
        List<Object[]> values;
        ExtendedObject header;
        
        for (String modelname : models.keySet()) {
            model = models.get(modelname);
            model.setPackage(state.pkgname);
            
            if (state.documents.getModel(modelname) != null) {
                if (state.documents.updateModel(model) < 0)
                    throw new IocasteException(new StringBuilder("error on "
                            + "update ").
                            append(modelname).
                            append(" model.").toString());
            } else {
                if (state.documents.createModel(model) < 0)
                    throw new IocasteException(new StringBuilder("error on "
                            + "create ").
                            append(modelname).
                            append(" model.").toString());
            }
            
            Registry.add(modelname, "MODEL", state);
            
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
                    header.set(modelitem, line[i++]);
                
                state.documents.save(header);
            }
        }
    }
    
    public static final void update(Map<String, DocumentModel> models,
            State state) throws Exception {
        String name, modelname;
        Set<DocumentModelItem> itens;
        int i;
        List<Object[]> values;
        ExtendedObject header;
        
        for (DocumentModel model : models.values()) {
            modelname = model.getName();
            if (state.documents.getModel(modelname) != null) {
                if (state.documents.updateModel(model) < 0)
                    throw new IocasteException("update model error.");
            } else {
                if (state.documents.createModel(model) < 0)
                    throw new IocasteException("create model error.");
                Registry.add(modelname, "MODEL", state);
            }
            
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
                    header.set(modelitem, line[i++]);
                
                state.documents.modify(header);
            }
        }
    }

}
