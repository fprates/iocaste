package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DocumentExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) {
        DocumentExtractor extractor;
        DataConversion conversion;
        ComplexModel cmodel;
        String keyname;
        Context extcontext;
        
        extcontext = getExtendedContext();
        cmodel = new Documents(context.function).
                getComplexModel(extcontext.link.cmodel);
        conversion = new DataConversion();
        conversion.dfsource("base");
        for (DocumentModelKey key : cmodel.getHeader().getKeys()) {
            keyname = key.getModelItemName();
            conversion.constant(keyname, getdfkey("head"));
            if ((extcontext.link.number != null) && (extcontext.id == null))
                conversion.nextnumber(keyname,
                        extcontext.link.number, extcontext.link.numberseries);
            break;
        }
        
        extractor = documentExtractorInstance(extcontext.link.cmodel);
        extractor.setHeader(conversion);
        extractor.ignoreInitialHead();
        extractor.setNS(extcontext.ns);
        tabs(extractor, context, cmodel);
        extcontext.document = extractor.save();
        extcontext.id = extcontext.document.getKey();
        
        message(Const.STATUS, "record.saved", extcontext.id);
    }
    
    protected void tabs(DocumentExtractor extractor,
            PageBuilderContext context, ComplexModel cmodel) {
        DataConversion conversion;
        ViewComponents components;
        DocumentModel model;
        TableToolData tabletool;
        String tbname, itemname;
        
        components = context.getView().getComponents();
        for (String name : cmodel.getItems().keySet()) {
            model = cmodel.getItems().get(name).model;
            if (model == null)
                continue;
            tbname = name.concat("_table");
            conversion = new DataConversion(model.getName());
            conversion.tbsource(tbname);
            extractor.add(conversion);

            tabletool = components.getComponentData(tbname);
            for (DocumentModelItem item : model.getItens()) {
                itemname = item.getName();
                if ((tabletool.itemcolumn != null &&
                        tabletool.itemcolumn.equals(itemname)) ||
                        model.isKey(item))
                    conversion.ignore(itemname);
            }
        }
    }

}
