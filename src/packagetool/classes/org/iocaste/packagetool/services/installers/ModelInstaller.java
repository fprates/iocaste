package org.iocaste.packagetool.services.installers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.IocasteException;
import org.iocaste.shell.common.SHLib;

public class ModelInstaller
        extends AbstractModuleInstaller<String, DocumentModel> {
    private Documents documents;
    private SHLib shlib;
    
    public ModelInstaller(Services services) {
        super(services, "NAME");
    }
    
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
    
    private final DocumentModel fixPackageReference(
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

    @Override
    protected final String getObjectName(String key, DocumentModel model) {
        return model.getName();
    }
    
    @Override
    public final void init(Function function) {
        documents = new Documents(function);
        shlib = new SHLib(function);
    }
    
    @Override
    public final void install(State state) throws Exception {
        installAll(state, state.data.getModels());
    }
    
    @Override
    protected final void install(State state, String key, DocumentModel model) {
        int i;
        List<Object[]> values;
        ExtendedObject header;
        DocumentModelItem[] items;
        
        model.setPackage(state.pkgname);
        extractsh(model, state);
        
        /*
         * recupera modelo para trazer as queries.
         */
        model = documents.getModel(model.getName());
        if (model == null)
            return;
        
        items = model.getItens();
        for (DocumentModelItem item : items)
            if (item.getSearchHelp() != null)
                shlib.assign(item);
        
        values = state.data.getValues(model);
        if (values == null)
            return;
        
        for (Object[] line : values) {
            header = new ExtendedObject(model);
            i = 0;
            
            for (DocumentModelItem modelitem : items)
                header.set(modelitem, line[i++]);
            
            documents.save(header);
        }
    }
    
    @Override
    protected final void installAll(
            State state, Map<String, DocumentModel> objects) throws Exception {
        DocumentModel[] models = prepare(objects, null, state);
        super.installAll(state, models);
    }
    
    private final DocumentModel[] prepare(Map<String, DocumentModel> models,
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
        error = documents.createModels(_models);
        if (error < 0)
            throw new IocasteException("error creating models.");
        return _models;
    }
    
    @Override
    public final void remove(ExtendedObject object) {
        String name = getObjectName(object);
        DocumentModel model = documents.getModel(name);
        if (model == null)
            return;
        for (DocumentModelItem item : model.getItens())
            if (item.getSearchHelp() != null)
                shlib.unassign(item);
        documents.removeModel(name);
        documents.delete(object);
    }
    
    @Override
    public final void update(State state) throws Exception {
        updateAll(state, state.data.getModels());
    }
    
    @Override
    protected final void update(State state, String key, DocumentModel model)
            throws Exception {
        int i;
        List<Object[]> values;
        ExtendedObject header;
        
        if (documents.updateModel(model) < 0)
            throw new IocasteException("update model error.");
        
        extractsh(model, state);
        
        /*
         * recupera modelo para trazer as queries.
         */
        model = documents.getModel(model.getName());
        values = state.data.getValues(model);
        if (values == null)
            return;
        
        for (Object[] line : values) {
            header = new ExtendedObject(model);
            i = 0;
            
            for (DocumentModelItem modelitem : model.getItens())
                header.set(modelitem, line[i++]);
            
            documents.modify(header);
        }
    }
    
    @Override
    protected final void updateAll(
            State state, Map<String, DocumentModel> models) throws Exception {
        DocumentModel model;
        List<String> toinstall;
        DocumentModel[] _models;
        
        toinstall = new ArrayList<>();
        for (String name : models.keySet()) {
            model = models.get(name);
            if (documents.getModel(name) != null) {
                update(state, name, model);
                continue;
            }
            toinstall.add(name);
        }
        
        if (toinstall.size() == 0)
            return;
        _models = prepare(models, toinstall, state);
        super.installAll(state, _models);
        
    }
}

