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
    
    private static final DocumentModel fixPackageReference(
            Map<String, DocumentModel> models, String name, State state) {
        DocumentModel model, modelreference;
        DocumentModelItem reference;
        
        model = models.get(name);
        model.setPackage(state.pkgname);
        for (DocumentModelItem item : model.getItens()) {
            reference = item.getReference();
            if (reference == null)
                continue;
            modelreference = reference.getDocumentModel();
            if (!models.containsKey(modelreference.getName()))
                continue;
            if (modelreference.getPackage() == null)
                modelreference.setPackage(state.pkgname);
        }
        
        return model;
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
        if (model == null)
            return;
        
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
    
    public static final void installAll(Map<String, DocumentModel> models,
            List<String> toinstall, State state) throws Exception {
        int error, i;
        DocumentModel[] _models;
        
        i = 0;
        if (toinstall != null) {
            _models = new DocumentModel[toinstall.size()];
            for (String name : toinstall)
                _models[i++] = fixPackageReference(models, name, state);
        } else {
            _models = new DocumentModel[models.size()];
            for (String name : models.keySet())
                _models[i++] = fixPackageReference(models, name, state);
        }
        error = state.documents.createModels(_models);
        if (error < 0)
            throw new IocasteException(
                    new StringBuilder("error creating models.").toString());
        
        for (DocumentModel installed : _models)
            install(installed, installed.getName(), state);
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
    
    public static final void updateAll(
            Map<String, DocumentModel> models, State state) throws Exception {
        DocumentModel model;
        List<String> toinstall;
        
        toinstall = new ArrayList<>();
        for (String name : models.keySet()) {
            model = models.get(name);
            if (state.documents.getModel(name) != null) {
                update(model, name, state);
                continue;
            }
            toinstall.add(name);
        }
        
        if (toinstall.size() == 0)
            return;
        installAll(models, toinstall, state);
    }
}
