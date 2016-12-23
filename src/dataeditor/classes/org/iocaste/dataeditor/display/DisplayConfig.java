package org.iocaste.dataeditor.display;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.dataeditor.Context;
import org.iocaste.dataeditor.TableConfig;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;

public class DisplayConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableConfig config = new TableConfig();
        
        config.context = context;
        config.mode = TableTool.DISPLAY;
        configTable(config);
    }
    
    protected final void configTable(TableConfig config) {
        TableToolData ttdata;
        Context extcontext;
        Documents documents;
        
        extcontext = getExtendedContext();
        getNavControl().setTitle(extcontext.model);
        
        ttdata = getTool("items");
        ttdata.context = config.context;
        ttdata.name = "items";
        ttdata.model = extcontext.model;
        ttdata.vlines = 0;
        ttdata.mode = config.mode;
        ttdata.mark = config.mark;

        config.properties = GetFieldsProperties.
                execute(config.context, extcontext.appname);
        
        documents = new Documents(config.context.function);
        config.model = documents.getModel(extcontext.model);

        for (DocumentModelItem item : config.model.getItens())
            configTableColumn(config, ttdata, item);
    }
    
    protected final void configTableColumn(
            TableConfig config, TableToolData ttdata, DocumentModelItem item) {
        TableToolColumn ttcolumn;
        FieldProperty property;
        String name = item.getName();
        
        ttcolumn = ttdata.instance(name);
        if (config.model.isKey(item) || (config.mode == TableTool.DISPLAY))
            ttcolumn.disabled = true;
        
        if (config.properties == null)
            return;
        
        property = config.properties.get(name);
        if (property == null)
            return;
        
        ttcolumn.componenttype = property.type;
        ttcolumn.values = property.values;
    }
}
