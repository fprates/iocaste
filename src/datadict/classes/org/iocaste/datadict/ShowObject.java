package org.iocaste.datadict;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DataConversionRule;
import org.iocaste.appbuilder.common.ObjectExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;

public class ShowObject extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Query query;
        ExtendedObject object;
        DataConversion conversion;
        List<ExtendedObject> objects;
        ExtendedObject[] items, dataelements, keys;
        ObjectExtractor extractor;
        Context extcontext = new Context();
        String name = getdfst("model", "NAME");
        Documents documents = new Documents(context.function);
        
        if (documents.getModel(name) == null) {
            message(Const.ERROR, "model.not.found");
            return;
        }
        
        extcontext.modelname = name;
        extcontext.head = documents.getObject("MODEL", name);
        
        query = new Query();
        query.setModel("MODELITEM");
        query.orderBy("INDEX");
        query.andEqual("MODEL", name);
        items = documents.select(query);
        
        query = new Query();
        query.setModel("DATAELEMENT");
        query.forEntries(items);
        query.andEqualEntries("NAME", "ELEMENT");
        dataelements = documents.select(query);
        
        query = new Query();
        query.setModel("MODEL_KEYS");
        query.andEqual("MODEL", name);
        keys = documents.select(query);
        
        conversion = new DataConversion("DD_MODEL_ITEM");
        conversion.rule(new ModelItemRule(dataelements, keys));
        extractor = new ObjectExtractor(context, conversion);
        objects = new ArrayList<>();
        for (ExtendedObject item : items) {
            conversion.source(item);
            object = extractor.instance();
            objects.add(object);
        }
         
        extcontext.items = objects.toArray(new ExtendedObject[0]);
        context.setExtendedContext(Main.STRUCTURE, extcontext);
        redirect(Main.STRUCTURE);
    }
}

class ModelItemRule implements DataConversionRule {
    private ExtendedObject[] dataelements;
    private Set<String> keys;
    
    public ModelItemRule(ExtendedObject[] dataelements, ExtendedObject[] keys) {
        this.dataelements = dataelements;
        if (keys == null)
            return;
        
        this.keys = new HashSet<>();
        for (ExtendedObject object : keys)
            this.keys.add(object.getst("NAME"));
    }

    @Override
    public void afterConversion(ExtendedObject object) {
        String name = object.getst("ELEMENT");
        
        for (ExtendedObject dataelement : dataelements) {
            if (!name.equals(dataelement.getst("NAME")))
                continue;
            Documents.moveOnly(object, dataelement, "LENGTH", "DECIMALS");
            switch(dataelement.geti("TYPE")) {
            case DataType.CHAR:
                object.set("TYPE", "CHAR");
                break;
            case DataType.NUMC:
                object.set("TYPE", "NUMC");
                break;
            case DataType.DEC:
                object.set("TYPE", "DEC");
                break;
            case DataType.DATE:
                object.set("TYPE", "DATE");
                break;
            case DataType.BOOLEAN:
                object.set("TYPE", "BOOL");
                break;
            }
        }
        
        if (keys != null) {
            name = object.getst("NAME");
            object.set("KEY", keys.contains(name));
        }
        
        name = object.getst("NAME").split("\\.")[1];
        object.set("NAME", name);
    }

    @Override
    public void beforeConversion(ExtendedObject object) { }
}
