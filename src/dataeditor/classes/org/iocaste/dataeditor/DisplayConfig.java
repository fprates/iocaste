package org.iocaste.dataeditor;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class DisplayConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableConfig config = new TableConfig();
        
        config.context = context;
        config.mode = TableTool.DISPLAY;
        configTable(config);
    }

    private Map<String, FieldProperty> getFieldsProperties(
            PageBuilderContext context, Context extcontext) {
        String url;
        GenericService service;
        Message message;
        Map<String, FieldProperty> fields;
        
        if (extcontext.appname == null)
            return null;
        
        url = new StringBuilder("/").append(extcontext.appname).
                append("/view.html").toString();
        
        message = new Message("fields_properties_get");
        message.add("page", context.view.getPageName());
        service = new GenericService(context.function, url);
        try {
            fields = service.invoke(message);
            return fields;
        } catch (Exception e) {
            return null;
        }
    }
    
    protected final void configTable(TableConfig config) {
        TableToolData ttdata;
        Context extcontext;
        Documents documents;
        
        StyleSettings.execute(config.context);
        
        extcontext = getExtendedContext();
        getNavControl().setTitle(extcontext.model);
        
        ttdata = getTableTool("items");
        ttdata.context = config.context;
        ttdata.name = "items";
        ttdata.model = extcontext.model;
        ttdata.vlines = 0;
        ttdata.mode = config.mode;
        ttdata.mark = config.mark;

        config.properties = getFieldsProperties(config.context, extcontext);
        
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
        
        ttcolumn = new TableToolColumn(ttdata, name);
        if (config.model.isKey(item) || (config.mode == TableTool.DISPLAY))
            ttcolumn.disabled = true;
        
        if (config.properties == null)
            return;
        
        property = config.properties.get(name);
        if (property == null)
            return;
        
        ttcolumn.type = property.type;
        ttcolumn.values = property.values;
    }
}

class TableConfig {
    public byte mode;
    public boolean mark;
    public PageBuilderContext context;
    public Map<String, FieldProperty> properties;
    public DocumentModel model;
}