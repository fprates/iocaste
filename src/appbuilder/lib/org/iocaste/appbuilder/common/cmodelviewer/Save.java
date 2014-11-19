package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DocumentExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) {
        DocumentModel model;
        DocumentExtractor extractor;
        DataConversion conversion;
        ComplexModel cmodel;
        String keyname;
        Context extcontext = getExtendedContext();
        
        cmodel = getManager(extcontext.cmodel).getModel();
        conversion = new DataConversion();
        conversion.dfsource("base");
        for (DocumentModelKey key : cmodel.getHeader().getKeys()) {
            keyname = key.getModelItemName();
            conversion.constant(keyname, getdfkey("head"));
            if ((extcontext.number != null) && (extcontext.id == null))
                conversion.nextnumber(keyname, extcontext.number);
            break;
        }
        
        extractor = documentExtractorInstance(extcontext.cmodel);
        extractor.setHeader(conversion);
        extractor.ignoreInitialHead();
        
        for (String name : cmodel.getItems().keySet()) {
            model = cmodel.getItems().get(name);
            conversion = new DataConversion(model.getName());
            extractor.addItems(name.concat("_table"), conversion);
            
            for (DocumentModelItem item : model.getItens())
                if (model.isKey(item))
                    conversion.ignore(item.getName());
        }
        
        if (extcontext.id == null)
            inputrefresh();
        
        extcontext.document = extractor.save();
        extcontext.id = extcontext.document.getKey();
        
        managerMessage(extcontext.cmodel, Const.STATUS, Manager.SAVED);
    }

}
