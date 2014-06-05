package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.DataConversion;
import org.iocaste.appbuilder.common.DocumentExtractor;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.shell.common.Const;

public class Save extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) {
        DocumentExtractor extractor;
        DataConversion conversion;
        ComplexModel cmodel = getManager().getModel();
        
        conversion = new DataConversion();
        for (DocumentModelKey key : cmodel.getHeader().getKeys()) {
            conversion.constant(key.getModelItemName(), getdfkeyst("head"));
            break;
        }
        
        extractor = documentExtractorInstance();
        extractor.setHeader("base", conversion);
        for (String name : cmodel.getItems().keySet())
            extractor.addItems(name.concat("_table"));
        extractor.save();
        
        managerMessage(Const.STATUS, Manager.SAVED);

    }

}
