package org.iocaste.dataeditor.entry.select;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.dataeditor.Context;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;

public class SelectEntryConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        DataFormToolData dataform;
        DocumentModel model;
        
        getNavControl().setTitle("select.entry");
        
        extcontext = getExtendedContext();
        dataform = getTool("selection");
        dataform.model = extcontext.model;

        model = new Documents(context.function).getModel(extcontext.model);
        for (DocumentModelItem item : model.getItens()) {
            if (model.isKey(item))
            	dataform.instance(item.getName()).required = true;
            else
            	dataform.instance(item.getName()).invisible = true;
        }
    }

}
