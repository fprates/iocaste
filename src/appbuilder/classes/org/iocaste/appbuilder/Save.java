package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DocumentExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ComplexDocument document;
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
            if (extcontext.number != null)
                conversion.nextnumber(keyname, extcontext.number);
            break;
        }
        
        extractor = documentExtractorInstance(extcontext.cmodel);
        extractor.setHeader(conversion);
        extractor.ignoreInitialHead();
        for (String name : cmodel.getItems().keySet())
            extractor.addItems(name.concat("_table"));
        
        document = extractor.save();
        extcontext.id = document.getKey();
        
        managerMessage(extcontext.cmodel, Const.STATUS, Manager.SAVED);

    }

}
