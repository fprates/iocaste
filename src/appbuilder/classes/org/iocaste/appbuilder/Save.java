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
    private String cmodel;
    
    public Save(String cmodel) {
        this.cmodel = cmodel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DocumentExtractor extractor;
        DataConversion conversion;
        ComplexModel cmodel;
        
        cmodel = getManager(this.cmodel).getModel();
        conversion = new DataConversion();
        conversion.dfsource("base");
        for (DocumentModelKey key : cmodel.getHeader().getKeys()) {
            conversion.constant(key.getModelItemName(), getdfkey("head"));
            break;
        }
        
        extractor = documentExtractorInstance(this.cmodel);
        extractor.setHeader(conversion);
        for (String name : cmodel.getItems().keySet())
            extractor.addItems(name.concat("_table"));
        extractor.save();
        
        managerMessage(this.cmodel, Const.STATUS, Manager.SAVED);

    }

}
