package org.iocaste.workbench;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DocumentExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.cmodelviewer.Save;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class ProjectSave extends Save {
    
    @Override
    protected void refresh(PageBuilderContext context) {
        try {
            execute("validate");
        } catch (Exception e) {
            new RuntimeException(e);
        }
    }
    
    @Override
    protected void tabs(DocumentExtractor extractor, PageBuilderContext context,
            ComplexModel cmodel) {
        DataConversion conversion;
        ViewComponents components;
        DocumentModel model;
        TableToolData tabletool;
        String tbname, itemname;
        WorkbenchContext extcontext;
        List<ExtendedObject> items;
        ExtendedObject[] objects;
        
        extcontext = getExtendedContext();
        components = context.getView().getComponents();
        for (String name : cmodel.getItems().keySet()) {
            tbname = name.concat("_table");
            model = cmodel.getItems().get(name);
            conversion = new DataConversion(model.getName());
            
            switch (name) {
            case "model_item":
                items = new ArrayList<>();
                for (String modelname : extcontext.models.keySet()) {
                    objects = extcontext.models.get(modelname);
                    for (ExtendedObject object : objects)
                        if (!Documents.isInitial(object))
                            items.add(object);
                }
                conversion.source(items);
                break;
            case "screen_spec_item":
                break;
            default:
                conversion.tbsource(tbname);
                tabletool = components.getComponentData(tbname);
                for (DocumentModelItem item : model.getItens()) {
                    itemname = item.getName();
                    if ((tabletool.itemcolumn != null &&
                            tabletool.itemcolumn.equals(itemname)) ||
                            model.isKey(item))
                        conversion.ignore(itemname);
                }
                break;
            }
            extractor.add(conversion);
        }
    }
}
