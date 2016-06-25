package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;

public class DocumentExtractor {
    private PageBuilderContext context;
    private DataConversion hconversion;
    private List<DataConversion> conversions;
    private Documents documents;
    private boolean ignoreinitialhead;
    private Object ns;
    private ComplexModel cmodel;
    
    public DocumentExtractor(PageBuilderContext context, String cmodel) {
        this.context = context;
        conversions = new ArrayList<>();
        documents = new Documents(context.function);
        this.cmodel = documents.getComplexModel(cmodel);
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

    @SuppressWarnings("unchecked")
    private static ExtendedObject[] convertItems(DataConversion conversion,
            ViewComponents components) {
        String source;
        Collection<ExtendedObject> collection;
        ExtendedObject[] objects;
        TableToolData ttdata;
        List<TableToolItem> ttitems;
        int i;
        
        switch (conversion.getSourceType()) {
        case DataConversion.OBJECTS:
            return (ExtendedObject[])conversion.getSource();
        case DataConversion.COLLECTION:
            collection = (Collection<ExtendedObject>)conversion.getSource();
            if (collection == null)
                break;
            objects = new ExtendedObject[collection.size()];
            i = 0;
            for (ExtendedObject extobject : collection)
                objects[i++] = extobject;
            return objects;
        case DataConversion.TABLETOOL:
            source = (String)conversion.getSource();
            ttdata = components.getComponentData(source);
            ttitems = ttdata.getItems();
            if (ttitems == null)
                break;
            objects = new ExtendedObject[ttitems.size()];
            i = 0;
            for (TableToolItem item : ttitems)
                objects[i++] = item.object;
            return objects;
        default:
            break;
        }
        return null;
    }
    
    public static final ExtendedObject[] extractItems(
            PageBuilderContext context,
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
        
        if (objects == null) {
            objects = convertItems(
                    conversion, context.getView().getComponents());
            if (objects == null)
                return null;
        }
        
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
            
            object = conversion(
                    ns, object, model, conversion, documents, false);
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
        DocumentModel model;
        String to, source;
        ExtendedObject head;
        ExtendedObject[] objects;
        DataForm form;
        ComplexDocument document;
        ViewComponents components;
        
        if (hconversion == null)
            throw new RuntimeException("no conversion rule for header.");

        head = null;
        document = new ComplexDocument(cmodel);
        components = context.getView().getComponents();

        switch (hconversion.getSourceType()) {
        case DataConversion.DATAFORM:
            source = (String)hconversion.getSource();
            if (source == null)
                break;
            
            form = components.entries.get(source).component.getElement();
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
            model = cmodel.getHeader();
        else
            model = documents.getModel(to);
        
        head = conversion(
                ns, head, model, hconversion, documents, ignoreinitialhead);
        document.setHeader(head);
        document.remove();
        for (DataConversion conversion : conversions) {
            objects = convertItems(conversion, components);
            extractItems(context, ns, documents, conversion, document, objects);
        }
        
        document.setNS(ns);
        
        return documents.save(document);
    }
    
    public final void setHeader(DataConversion conversion) {
        hconversion = conversion;
    }
    
    public final void setNS(Object ns) {
        this.ns = ns;
    }
}
