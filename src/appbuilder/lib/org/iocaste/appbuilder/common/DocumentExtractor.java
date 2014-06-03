package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.TableTool;

public class DocumentExtractor {
    private Manager manager;
    private PageBuilderContext context;
    private String form;
    private DataConversion hconversion;
    private Map<String, DataConversion> items;
    private Documents documents;
    
    public DocumentExtractor(PageBuilderContext context) {
        this.context = context;
        items = new HashMap<>();
        documents = new Documents(context.function);
    }
    
    private ExtendedObject conversion(ExtendedObject source,
            DocumentModel resultmodel, DataConversion conversion) {
        Map<String, Object> hold;
        ExtendedObject object;
        Set<String> fields;
        Object value;
        
        if (conversion == null)
            return source;
        
        fields = conversion.getFields();
        hold = push(source, conversion);
        if (Documents.isInitial(source)) {
            pop(source, hold);
            return null;
        }
        
        pop(source, hold);
        object = new ExtendedObject(resultmodel);
        Documents.move(object, source);
        
        for (String field : fields) {
            value = conversion.getValue(field);
            switch (conversion.getType(field)) {
            case DataConversion.CONSTANT:
                object.set(field, value);
                break;
            case DataConversion.NEXT_NUMBER:
                object.set(field, documents.getNextNumber((String)value));
                break;
            }
        }
        
        return object;
    }
    
    private final void pop(ExtendedObject object, Map<String, Object> hold) {
        for (String field : hold.keySet())
            object.set(field, hold.get(field));
    }
    
    private final Map<String, Object> push(
            ExtendedObject object, DataConversion conversion) {
        Map<String, Object> hold = new HashMap<>();
        
        for (String field : conversion.getFields()) {
            if (conversion.getType(field) != DataConversion.IGNORE)
                continue;
            
            hold.put(field, object.get(field));
            Documents.clear(object, field);
        }
        
        return hold;
    }
    
    public final void save() {
        TableTool tabletool;
        DocumentModel model;
        DataConversion conversion;
        String to;
        Map<String, DocumentModel> models = new HashMap<>();
        String pagename = context.view.getPageName();
        ComplexDocument document = manager.instance();
        DataForm form = context.view.getElement(this.form);
        
        model = documents.getModel(hconversion.getTo());
        document.setHeader(conversion(form.getObject(), model, hconversion));
        for (String name : items.keySet()) {
            tabletool = context.getViewComponents(pagename).
                    tabletools.get(name);
            
            for (ExtendedObject object : tabletool.getObjects()) {
                conversion = items.get(name);
                if (conversion == null) {
                    document.add(object);
                    continue;
                }
                
                to = conversion.getTo();
                model = models.get(to);
                if (model == null) {
                    model = documents.getModel(to);
                    if (model == null)
                        throw new RuntimeException(
                                to.concat(" is an invalid model."));
                    
                    models.put(to, model);
                }
                
                object = conversion(object, model, conversion);
                if (object == null)
                    continue;
                
                document.add(object);
            }
        }
        
        manager.save(document);
    }
    
    public final void setHeader(String form, DataConversion conversion) {
        this.form = form;
        hconversion = conversion;
    }
    
    public final void setItems(String tabletool) {
        setItems(tabletool, null);
    }
    
    public final void setItems(String tabletool, DataConversion conversion) {
        items.put(tabletool, conversion);
    }
    
    public final void setManager(Manager manager) {
        this.manager = manager;
    }
}
