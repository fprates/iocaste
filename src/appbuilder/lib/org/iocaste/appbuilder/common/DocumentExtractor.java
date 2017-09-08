package org.iocaste.appbuilder.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.dataformtool.DataFormTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.managedview.DataConversion;
import org.iocaste.runtime.common.managedview.DataConversionRule;

public class DocumentExtractor {
    private PageBuilderContext context;
    private DataConversion hconversion;
    private List<DataConversion> conversions;
    private boolean ignoreinitialhead;
    private Object ns;
    private String cmodelname;
    
    public DocumentExtractor(PageBuilderContext context, String cmodelname) {
        this.context = context;
        conversions = new ArrayList<>();
        this.cmodelname = cmodelname;
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
    private static Collection<ExtendedObject> convertItems(
            DataConversion conversion, ViewComponents components) {
        String source;
        Collection<ExtendedObject> collection;
        ExtendedObject[] objects;
        TableToolData ttdata;
        Map<Integer, TableToolItem> ttitems;
        
        switch (conversion.getSourceType()) {
        case DataConversion.OBJECTS:
            objects = (ExtendedObject[])conversion.getSource();
            collection = new ArrayList<>();
            for (ExtendedObject object : objects)
                if (!Documents.isInitial(object))
                    collection.add(object);
            return collection;
        case DataConversion.COLLECTION:
            return (Collection<ExtendedObject>)conversion.getSource();
        case DataConversion.TABLETOOL:
            source = (String)conversion.getSource();
            ttdata = components.getComponentData(source);
            ttitems = ttdata.getItems();
            if (ttitems == null)
                break;
            collection = new ArrayList<>();
            for (TableToolItem item : ttitems.values())
                if (!Documents.isInitial(item.object))
                    collection.add(item.object);
            return collection;
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
            Collection<ExtendedObject> objects) {
        DataConversionRule rule;
        List<ExtendedObject> result;
        String to, sourcepage;
        DocumentModel model;
        ViewContext viewctx;
        Map<String, DocumentModel> models = new HashMap<>();
        
        if (objects == null) {
            sourcepage = conversion.getSourcePage();
            viewctx = (sourcepage == null)?
                    context.getView() : context.getView(sourcepage);
            objects = convertItems(conversion, viewctx.getComponents());
            if (objects == null)
                return null;
        }
        
        result = (document == null)? new ArrayList<ExtendedObject>() : null;
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
        String to, source, sourcepage;
        ExtendedObject head;
        Collection<ExtendedObject> objects;
        DataFormTool dftool;
        ComplexDocument document;
        ViewComponents components;
        DataConversionRule rule;
        ComplexModel cmodel;
        Documents documents;
        
        if (hconversion == null)
            throw new RuntimeException("no conversion rule for header.");

        head = null;
        documents = new Documents(context.function);
        cmodel = documents.getComplexModel(cmodelname);
        if (cmodel == null)
            throw new RuntimeException(
                    cmodelname.concat(" is an undefined cmodel."));
        
        document = new ComplexDocument(cmodel);
        sourcepage = hconversion.getSourcePage();
        components = (sourcepage == null)? context.getView().getComponents() :
            context.getView(sourcepage).getComponents();

        switch (hconversion.getSourceType()) {
        case DataConversion.DATAFORM:
            source = (String)hconversion.getSource();
            if (source == null)
                break;
            
            dftool = components.getComponent(source);
            head = dftool.getObject();
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
        
        rule = hconversion.getRule();
        if (rule != null)
            rule.beforeConversion(head);
        head = conversion(
                ns, head, model, hconversion, documents, ignoreinitialhead);
        if (rule != null)
            rule.afterConversion(head);
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
