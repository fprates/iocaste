package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.workbench.common.engine.handlers.ElementConfigHandler;

public class AutomatedConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        String element, name, spectype;
        Object value;
        ElementConfigHandler handler;
        String page = context.view.getPageName();
        Context extcontext = getExtendedContext();
        
        for (ConversionResult configitem : extcontext.views.get(page).
                getList("views.view.config")) {
            element = configitem.getst("views.view.config.item.element");
            name = configitem.getst("views.view.config.item.name");
            value = get(configitem, "views.view.config.item.value");
            
            spectype = extcontext.spec.items.get(element);
            handler = extcontext.config.handlers.get(spectype);
            handler.set(element, name, value);
        }
    }
    
    private final Object get(ConversionResult item, String tag) {
        int type = item.geti("views.view.config.item.type");
        String value = item.getst(tag);
        switch (type) {
        case DataType.BOOLEAN:
            return Boolean.parseBoolean(value);
        case DataType.BYTE:
            return Byte.parseByte(value);
        case DataType.CHAR:
            return value;
        case DataType.INT:
            return Integer.parseInt(value);
        case DataType.LONG:
            return Long.parseLong(value);
        }
        return value;
    }
}
