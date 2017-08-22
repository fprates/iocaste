package org.iocaste.runtime.common.managedview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DataConversionRule;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractPage;

public class DocumentExtractor {
    private Context context;
    private DataConversion hconversion;
    private List<DataConversion> conversions;
    private boolean ignoreinitialhead;
    private Object ns;
    private String cmodelname;
    
    public DocumentExtractor(Context context, String cmodelname) {
        this.context = context;
        conversions = new ArrayList<>();
        this.cmodelname = cmodelname;
    }
    
    public final void add(DataConversion conversion) {
        conversions.add(conversion);
    }
    
    private static ExtendedObject conversion(
            Context context,
            Object ns,
            ExtendedObject source,
            DocumentModel resultmodel,
            DataConversion conversion,
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
                object.set(
                        field,
                        context.runtime().getNSNextNumber((String)value,
                        ns,
                        (String)parameter));
                break;
            }
        }
        
        return object;
    }

    @SuppressWarnings("unchecked")
    private static Collection<ExtendedObject> convertItems(
            DataConversion conversion, AbstractPage page) {
        String source;
        Collection<ExtendedObject> collection;
        ExtendedObject[] objects;
        ToolData ttdata;
        
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
            ttdata = page.instance(source);
            collection = new ArrayList<>();
            for (ExtendedObject object : ttdata.objects.values())
                if (!Documents.isInitial(object))
                    collection.add(object);
            return collection;
        default:
            break;
        }
        return null;
    }
    
    public static final ExtendedObject[] extractItems(
            Context context,
            Object ns,
            DataConversion conversion,
            ComplexDocument document,
            Collection<ExtendedObject> objects) {
        AbstractPage page;
        DataConversionRule rule;
        List<ExtendedObject> result;
        String to, sourcepage;
        DocumentModel model;
        RuntimeEngine runtime;
        Map<String, DocumentModel> models = new HashMap<>();
        
        if (objects == null) {
            sourcepage = conversion.getSourcePage();
            page = (sourcepage == null)?
                    context.getPage() : context.getPage(sourcepage);
            objects = convertItems(conversion, page);
            if (objects == null)
                return null;
        }
        
        runtime = context.runtime();
        result = (document == null)? new ArrayList<>() : null;
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
                            "conversion has an undefined model.");
            } else {
                model = models.get(to);
                if (model == null) {
                    model = runtime.getModel(to);
                    if (model == null)
                        throw new IocasteException(
                                "%s is an invalid model.", to);
                    
                    models.put(to, model);
                }
            }

            rule = conversion.getRule();
            if (rule != null)
                rule.beforeConversion(object);
            
            object = conversion(
                    context,
                    ns,
                    object,
                    model,
                    conversion,
                    false);
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
        ToolData dftool;
        ComplexDocument document;
        DataConversionRule rule;
        ComplexModel cmodel;
        AbstractPage page;
        RuntimeEngine runtime;
        
        if (hconversion == null)
            throw new RuntimeException("no conversion rule for header.");

        head = null;
        runtime = context.runtime();
        cmodel = runtime.getComplexModel(cmodelname);
        if (cmodel == null)
            throw new RuntimeException(
                    cmodelname.concat(" is an undefined cmodel."));
        
        document = new ComplexDocument(cmodel);
        sourcepage = hconversion.getSourcePage();
        page = (sourcepage == null)?
                context.getPage() : context.getPage(sourcepage);

        switch (hconversion.getSourceType()) {
        case DataConversion.DATAFORM:
            source = (String)hconversion.getSource();
            if (source == null)
                break;
            
            dftool = page.instance(source);
            head = dftool.object;
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
            model = runtime.getModel(to);
        
        rule = hconversion.getRule();
        if (rule != null)
            rule.beforeConversion(head);
        head = conversion(context,
                ns, head, model, hconversion, ignoreinitialhead);
        if (rule != null)
            rule.afterConversion(head);
        document.setHeader(head);
        document.remove();
        for (DataConversion conversion : conversions) {
            objects = convertItems(conversion, page);
            extractItems(context, ns, conversion, document, objects);
        }
        
        document.setNS(ns);
        
        return runtime.save(document);
    }
    
    public final void setHeader(DataConversion conversion) {
        hconversion = conversion;
    }
    
    public final void setNS(Object ns) {
        this.ns = ns;
    }
}
