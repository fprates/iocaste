package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;

public class DocumentExtractor {
    private PageBuilderContext context;
    private DataConversion hconversion;
    private Map<String, DataConversion> items;
    private Documents documents;
    private Manager manager;
    
    public DocumentExtractor(PageBuilderContext context, String manager) {
        this.context = context;
        items = new HashMap<>();
        documents = new Documents(context.function);
        this.manager = context.getManager(manager);
    }
    
    public final void addItems(String tabletool) {
        addItems(tabletool, null);
    }
    
    public final void addItems(String tabletool, DataConversion conversion) {
        items.put(tabletool, conversion);
    }
    
    private static ExtendedObject conversion(
            ExtendedObject source,
            DocumentModel resultmodel,
            DataConversion conversion,
            Documents documents) {
        Map<String, Object> hold;
        ExtendedObject object;
        Set<String> fields, ignore;
        Object value;
        
        if (conversion == null)
            return source;
        
        fields = conversion.getFields();
        hold = push(source, conversion);
        ignore = new HashSet<>();
        for (String field : fields)
            if (conversion.getType(field) == DataConversion.IGNORE)
                ignore.add(field);
        
        if (Documents.isInitialIgnoring(source, ignore)) {
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
    
    public static final ExtendedObject[] extractItems(
            Documents documents,
            DataConversion conversion,
            ComplexDocument document,
            ExtendedObject[] objects) {
        DataConversionRule rule;
        List<ExtendedObject> result;
        String to;
        DocumentModel model;
        Map<String, DocumentModel> models = new HashMap<>();
        
        result = (document == null)? new ArrayList<ExtendedObject>() : null;
        
        if ((objects == null) &&
                (conversion.getSourceType() == DataConversion.OBJECTS))
            objects = (ExtendedObject[])conversion.getSource();
        
        for (ExtendedObject object : objects) {
            if (conversion == null) {
                if (document == null)
                    result.add(object);
                else
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

            rule = conversion.getRule();
            if (rule != null)
                rule.beforeConversion(object);
            
            object = conversion(object, model, conversion, documents);
            if (object == null)
                continue;
            
            if (rule != null)
                rule.afterConversion(object);
            
            if (document == null)
                result.add(object);
            else
                document.add(object);
        }
        
        return (document == null)? result.toArray(new ExtendedObject[0]) : null;
    }
    
    private static final void pop(
            ExtendedObject object, Map<String, Object> hold) {
        for (String field : hold.keySet())
            object.set(field, hold.get(field));
    }
    
    private static final Map<String, Object> push(
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
    
    public final ComplexDocument save() {
        DataConversion conversion;
        TableTool tabletool;
        DocumentModel model;
        String to, dfsource;
        ExtendedObject head;
        ExtendedObject[] objects;
        DataForm form;
        String pagename;
        ComplexDocument document;
        
        if (hconversion == null)
            throw new RuntimeException("no conversion rule for header.");

        head = null;
        pagename = context.view.getPageName();
        document = manager.instance();
        
        switch (hconversion.getSourceType()) {
        case DataConversion.DATAFORM:
            dfsource = (String)hconversion.getSource();
            if (dfsource == null)
                break;
            
            form = context.view.getElement(dfsource);
            head = form.getObject();
            break;
        case DataConversion.OBJECT:
            head = (ExtendedObject)hconversion.getSource();
            break;
        default:
            throw new RuntimeException("invalid conversion for header");
        }
        
        if (head == null)
            throw new RuntimeException("no header data.");
        
        to = hconversion.getTo();
        if (to == null)
            model = manager.getModel().getHeader();
        else
            model = documents.getModel(to);
        
        head = conversion(head, model, hconversion, documents);
        document.setHeader(head);
        for (String name : items.keySet()) {
            objects = null;
            conversion = items.get(name);
            if (conversion != null)
                switch (conversion.getSourceType()) {
                case DataConversion.OBJECTS:
                    objects = (ExtendedObject[])conversion.getSource();
                    break;
                }
            
            if (objects == null) {
                tabletool = context.getView(pagename).getComponents().
                        tabletools.get(name).component;
                objects = tabletool.getObjects();
            }
            
            if (objects == null)
                continue;

            extractItems(documents, conversion, document, objects);
        }
        
        manager.save(document);
        
        return document;
    }
    
    public final void setHeader(DataConversion conversion) {
        hconversion = conversion;
    }
}
