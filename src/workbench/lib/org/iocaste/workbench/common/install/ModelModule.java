package org.iocaste.workbench.common.install;

import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.protocol.utils.ConversionResult;

public class ModelModule extends AbstractInstallModule {
    private static final String KEY = "install.models.model.items.item.key";
    
    public ModelModule(ModuleContext context) {
        super(context, "install.models", "model");
        context.mapping.add("install.models.model.items", "item");
        context.mapping.setType(KEY, boolean.class);
    }
    
    @SuppressWarnings("unchecked")
    protected final void execute(ConversionResult map) {
        String name, table;
        List<ConversionResult> items;
        DocumentModel model;
        
        name = map.getst("install.models.model.name");
        table = map.getst("install.models.model.table");
        model = modulectx.context.getInstallData().
                getModel(name, table, null);
        items = (List<ConversionResult>)map.get("install.models.model.items");
        for (ConversionResult itemmap : items)
            itemconversion(model, itemmap);
    }
    
    private final void itemconversion(DocumentModel model, ConversionResult map)
    {
        DocumentModelItem item;
        boolean key;
        String name, field, element;
        
        name = map.getst("install.models.model.items.item.name");
        field = map.getst("install.models.model.items.item.field");
        element = map.getst("install.models.model.items.item.element");
        key = map.getbl(KEY);
        
        item = new DocumentModelItem(name);
        item.setTableFieldName(field);
        item.setDataElement(modulectx.elements.get(element));
        if (key)
            model.add(new DocumentModelKey(item));
        model.add(item);
    }
}
