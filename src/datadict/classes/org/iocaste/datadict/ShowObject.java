package org.iocaste.datadict;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DataConversionRule;
import org.iocaste.appbuilder.common.ObjectExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;

public class ShowObject extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject object;
        DataConversion conversion;
        List<ExtendedObject> objects;
        List<ExtendedObject> items;
        ObjectExtractor extractor;
        DocumentModel model;
        Context extcontext = new Context();
        String name = getdfst("model", "NAME");
        Documents documents = new Documents(context.function);
        
        model = documents.getModel(name);
        if (model == null) {
            message(Const.ERROR, "model.not.found");
            return;
        }
        
        extcontext.modelname = name;
        extcontext.head = instance("MODEL");
        extcontext.head.setInstance(model);
        
        items = new ArrayList<>();
        for (DocumentModelItem item : model.getItens()) {
            object = instance("MODELITEM");
            object.setInstance(item);
            items.add(object);
        }
        
        conversion = new DataConversion("DD_MODEL_ITEM");
        conversion.rule(new ModelItemRule(model));
        extractor = new ObjectExtractor(context, conversion);
        objects = new ArrayList<>();
        for (ExtendedObject item : items) {
            conversion.source(item);
            object = extractor.instance();
            objects.add(object);
        }
         
        extcontext.items = objects.toArray(new ExtendedObject[0]);
        context.getView(Main.STRUCTURE).set(extcontext);
        redirect(Main.STRUCTURE);
    }
}

class ModelItemRule implements DataConversionRule {
    private DocumentModel model;
    
    public ModelItemRule(DocumentModel model) {
        this.model = model;
    }

    @Override
    public void afterConversion(ExtendedObject object) {
        String name;
        DataElement dataelement = object.get("ELEMENT");
        
        object.set("ELEMENT", dataelement.getName());
        object.set("LENGTH", dataelement.getLength());
        object.set("DECIMALS", dataelement.getDecimals());
        switch(dataelement.getType()) {
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
        
        name = object.getst("NAME");
        object.set("KEY", model.isKey(model.getModelItem(name)));
    }

    @Override
    public void beforeConversion(ExtendedObject object) { }
}
