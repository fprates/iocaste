package org.iocaste.dataeditor.entry;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.dataeditor.Context;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;

public class EntryConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        String name;
        Context extcontext;
        DataFormToolData dataform;
        DataFormToolItem item;
        DocumentModel model;
        FieldProperty property;
        boolean keyset, focusset;
        
        getNavControl().setTitle("add.entry");
        
        extcontext = getExtendedContext();
        model = new Documents(context.function).getModel(extcontext.model);
        
        dataform = getDataFormTool("detail");
        dataform.model = model;

        extcontext.properties =
                GetFieldsProperties.execute(context, extcontext.appname);
        keyset = focusset = false;
        for (DocumentModelItem mitem : model.getItens()) {
            name = mitem.getName();
            item = dataform.itemInstance(name);
            if ((extcontext.number != null) && !keyset)
                if (model.isKey(mitem))
                    item.disabled = keyset = true;
            
            if (!focusset && !item.disabled)
                item.focus = focusset = true;
            
            if (extcontext.properties == null)
                continue;
            
            property = extcontext.properties.get(name);
            if (property == null)
                continue;
            
            item.componenttype = property.type;
        }
    }
}
