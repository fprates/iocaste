package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.tabletool.TableToolEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;

public class DocumentExtractor {
    private PageBuilderContext context;
    private DataConversion hconversion;
    private List<DataConversion> conversions;
    private Documents documents;
    private Manager manager;
    private boolean ignoreinitialhead;
    private Object ns;
    
    public DocumentExtractor(PageBuilderContext context, String manager) {
        this.context = context;
        conversions = new ArrayList<>();
        documents = new Documents(context.function);
        this.manager = context.getManager(manager);
    }
    
    public final void add(DataConversion conversion) {
        conversions.add(conversion);
    }
    
    private static ExtendedObject conversion(
            Object ns,
            ExtendedObject source,
            DocumentModel resultmodel,
            DataConversion conversion,
            Documents documents,
            boolean ignoreinitial) {
        Map<String, Object> hold;
        ExtendedObject object;
        Set<String> fields, ignore;
        Object value, parameter;
        
        if (conversion == null)
            return source;
        
        fields = conversion.getFields();
        if (!ignoreinitial) {
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
        }
        
        object = new ExtendedObject(resultmodel);
        Documents.move(object, source);
        
        for (String field : fields) {
            parameter = conversion.getParameter(field);
            value = conversion.getValue(field);
            switch (conversion.getType(field)) {
            case DataConversion.CONSTANT:
                object.set(field, value);
                break;
            case DataConversion.NEXT_NUMBER:
                object.set(field, documents.
                        getNSNextNumber((String)value, ns, (String)parameter));
                break;
            }
        }
        
        return object;
    }
    
    public static final ExtendedObject[] extractItems(
            Object ns,
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
            if (to == null) {
                model = object.getModel();
                if (model == null)
                    throw new RuntimeException(
                            "conversion has an undefined model");
            } else {
                model = models.get(to);
                if (model == null) {
                    model = documents.getModel(to);
                    if (model == null)
                        throw new RuntimeException(
                                to.concat(" is an invalid model."));
                    
                    models.put(to, model);
                }
            }

            rule = conversion.getRule();
            if (rule != null)
                rule.beforeConversion(object);
            
            object = conversion(ns, object, model, conversion, documents, false);
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
    
    public final void ignoreInitialHead() {
        ignoreinitialhead = true;
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
        int i;
        List<TableToolItem> ttitems;
        TableToolEntry entry;
        DocumentModel model;
        String to, source;
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
            source = (String)hconversion.getSource();
            if (source == null)
                break;
            
            form = context.view.getElement(source);
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
        
        head = conversion(
                ns, head, model, hconversion, documents, ignoreinitialhead);
        document.setHeader(head);
        for (DataConversion conversion : conversions) {
            switch (conversion.getSourceType()) {
            case DataConversion.OBJECTS:
                objects = (ExtendedObject[])conversion.getSource();
                break;
            case DataConversion.TABLETOOL:
                source = (String)conversion.getSource();
                entry = context.getView(pagename).getComponents().
                        tabletools.get(source);
                ttitems = entry.data.getItems();
                if (ttitems == null)
                    continue;
                
                objects = new ExtendedObject[ttitems.size()];
                i = 0;
                for (TableToolItem item : ttitems)
                    objects[i++] = item.object;
                break;
            default:
                continue;
            }

            extractItems(ns, documents, conversion, document, objects);
        }
        
        document.setNS(ns);
        
        return manager.save(document);
    }
    
    public final void setHeader(DataConversion conversion) {
        hconversion = conversion;
    }
    
    public final void setNS(Object ns) {
        this.ns = ns;
    }
}
