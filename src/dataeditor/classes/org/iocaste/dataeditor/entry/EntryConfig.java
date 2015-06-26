package org.iocaste.dataeditor.entry;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.dataeditor.Context;
import org.iocaste.dataeditor.GetFieldsProperties;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;

public class EntryConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        DataForm dataform;
        DataItem item;
        DocumentModel model;
        FieldProperty property;
        Map<String, FieldProperty> properties;
        boolean keyset, focusset;
        
        getNavControl().setTitle("add.entry");
        
        extcontext = getExtendedContext();
        model = new Documents(context.function).getModel(extcontext.model);
        
        dataform = getElement("detail");
        dataform.importModel(model);

        properties = GetFieldsProperties.execute(context, extcontext);
        keyset = focusset = false;
        for (Element element : dataform.getElements()) {
            item = (DataItem)element;
            if ((extcontext.number != null) && !keyset) {
                if (model.isKey(item.getModelItem())) {
                    item.setEnabled(false);
                    keyset = true;
                }
            }
            
            if (!focusset && element.isEnabled()) {
                context.view.setFocus(element);
                focusset = true;
            }
            
            if (properties == null)
                continue;
            
            property = properties.get(item.getName());
            if (property == null)
                continue;
            
            if (property.type != null)
                item.setComponentType(property.type);
            
            if (property.values == null)
                continue;
            
            for (String key : property.values.keySet())
                item.add(key, property.values.get(key));
        }
    }
}
