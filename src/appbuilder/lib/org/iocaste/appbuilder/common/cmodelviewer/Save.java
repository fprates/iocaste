package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DocumentExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ViewComponents components;
        DocumentModel model;
        DocumentExtractor extractor;
        DataConversion conversion;
        ComplexModel cmodel;
        String keyname, tbname, itemname;
        TableToolData tabletool;
        Context extcontext = getExtendedContext();
        
        cmodel = getManager(extcontext.link.cmodel).getModel();
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
        
        components = context.getView().getComponents();
        for (String name : cmodel.getItems().keySet()) {
            tbname = name.concat("_table");
            model = cmodel.getItems().get(name);
            conversion = new DataConversion(model.getName());
            conversion.tbsource(tbname);
            extractor.add(conversion);

            tabletool = components.tabletools.get(tbname).data;
            for (DocumentModelItem item : model.getItens()) {
                itemname = item.getName();
                if ((tabletool.itemcolumn != null &&
                        tabletool.itemcolumn.equals(itemname)) ||
                        model.isKey(item))
                    conversion.ignore(itemname);
            }
        }
        
        if (extcontext.id == null)
            inputrefresh();
        
        extcontext.document = extractor.save();
        extcontext.id = extcontext.document.getKey();
        
        managerMessage(extcontext.link.cmodel, Const.STATUS, Manager.SAVED,
                extcontext.id);
    }

}
