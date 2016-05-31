package org.iocaste.datadict;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DataConversionRule;
import org.iocaste.appbuilder.common.ObjectExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
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
        ObjectExtractor extractor;
        DocumentModel model;
        Context extcontext;
        TableToolContextEntry table;
        String name = getdfst("model", "NAME");
        
        model = new Documents(context.function).getModel(name);
        if (model == null) {
            message(Const.ERROR, "model.not.found");
            return;
        }
        
        extcontext = getExtendedContext();
        init(Main.STRUCTURE, extcontext);
        object = instance("MODEL");
        object.setInstance(model);
        extcontext.dataformInstance(Main.STRUCTURE, "head");
        extcontext.set(Main.STRUCTURE, "head", object);

        objects = new ArrayList<>();
        for (DocumentModelItem item : model.getItens()) {
            object = instance("MODELITEM");
            object.setInstance(item);
            objects.add(object);
        }

        table = extcontext.tableInstance(Main.STRUCTURE, "items");
        table.items.clear();
        conversion = new DataConversion("DD_MODEL_ITEM");
        conversion.rule(new ModelItemRule(model));
        extractor = new ObjectExtractor(context, conversion);
        for (ExtendedObject item : objects) {
            conversion.source(item);
            extcontext.add(Main.STRUCTURE, "items", extractor.instance());
        }
        
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
