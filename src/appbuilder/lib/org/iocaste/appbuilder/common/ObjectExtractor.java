package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.managedview.DataConversion;
import org.iocaste.runtime.common.managedview.DataConversionRule;
import org.iocaste.shell.common.AbstractContext;

public class ObjectExtractor {
    private DataConversion conversion;
    private Documents documents;
    
    public ObjectExtractor(AbstractContext context, DataConversion conversion) {
        this.conversion = conversion;
        documents = new Documents(context.function);
    }
    
    public final ExtendedObject instance() {
        DataConversionRule rule;
        ExtendedObject[] sources;
        ExtendedObject object;
        DocumentModel model;
        
        model = documents.getModel(conversion.getTo());
        object = new ExtendedObject(model);
        switch (conversion.getSourceType()) {
        case DataConversion.OBJECTS:
            sources = (ExtendedObject[])conversion.getSource();
            break;
        case DataConversion.OBJECT:
            sources = new ExtendedObject[1];
            sources[0] = (ExtendedObject)conversion.getSource();
            break;
        default:
            return null;
        }
        
        rule = conversion.getRule();
        if (rule != null)
            rule.beforeConversion(object);
        for (ExtendedObject source : sources)
            Documents.move(object, source);
        for (String field : conversion.getFields()) {
            switch (conversion.getType(field)) {
            case DataConversion.CONSTANT:
                object.set(field, conversion.getValue(field));
                break;
            case DataConversion.NEXT_NUMBER:
                object.set(field, documents.
                        getNextNumber((String)conversion.getValue(field)));
                break;
            }
        }
        
        if (rule != null)
            rule.afterConversion(object);
        
        return object;
    }
}
