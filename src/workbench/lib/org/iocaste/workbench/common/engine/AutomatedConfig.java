package org.iocaste.workbench.common.engine;

import java.util.List;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.workbench.common.engine.handlers.ElementConfigHandler;

public class AutomatedConfig extends AbstractViewConfig {

    private final void elementsconfig(Context extcontext,
            String specname, List<ConversionResult> items) {
        String name, spectype;
        Object value;
        ElementConfigHandler handler;

        for (ConversionResult configitem : items) {
            name = configitem.getst(
                    "views.view.spec.item.config.item.name");
            value = get(configitem,
                    "views.view.spec.item.config.item.value");
            
            spectype = extcontext.spec.items.get(specname);
            handler = extcontext.config.handlers.get(spectype);
            handler.set(specname, name, value);
        }
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        String specname;
        AbstractComponentData tool;
        List<ConversionResult> items;
        
        String page = context.view.getPageName();
        Context extcontext = getExtendedContext();

        for (ConversionResult specitem : extcontext.views.get(page).
                getList("views.view.spec")) {
            specname = specitem.getst("views.view.spec.item.name");
            tool = getTool(specname);
            items = specitem.getList("views.view.spec.item.config");
            if (items != null)
                elementsconfig(extcontext, specname, items);
            
            items = specitem.getList("views.view.spec.item.subitems");
            if (items != null)
                subitemsconfig(tool, items);
        }
    }
    
    private final Object get(ConversionResult item, String tag) {
        int type = item.geti("views.view.spec.item.config.item.type");
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
    
    private final void subitemsconfig(AbstractComponentData tool,
            List<ConversionResult> items) {
        String name;
        AbstractComponentDataItem item;
        
        for (ConversionResult subitem : items) {
            name = subitem.getst("views.view.spec.item.subitems.item.name");
            item = tool.instance(name);

            item.required = subitem.getbl(
                    "views.view.spec.item.subitems.item.required");
            item.invisible = subitem.getbl(
                    "views.view.spec.item.subitems.item.invisible");
            item.focus = subitem.getbl(
                    "views.view.spec.item.subitems.item.focus");
            item.length = subitem.geti(
                    "views.view.spec.item.subitems.item.length");
            item.vlength = subitem.geti(
                    "views.view.spec.item.subitems.item.vlength");
        }
    }
}
