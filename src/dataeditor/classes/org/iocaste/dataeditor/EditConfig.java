package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;

public class EditConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DocumentModel model;
        TableToolData ttdata;
        Context extcontext;
        Documents documents;

        StyleSettings.execute(context);
        
        extcontext = getExtendedContext();
        getNavControl().setTitle(extcontext.model);
        
        ttdata = getTableTool("items");
        ttdata.context = context;
        ttdata.name = "items";
        ttdata.model = extcontext.model;
        ttdata.vlines = 0;
        ttdata.mark = true;
        ttdata.mode = TableTool.UPDATE;

        documents = new Documents(context.function);
        model = documents.getModel(extcontext.model);
        for (DocumentModelKey key : model.getKeys())
            new TableToolColumn(ttdata, key.getModelItemName()).disabled = true;

    }

}
