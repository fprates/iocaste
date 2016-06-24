package org.iocaste.workbench.common.engine;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.protocol.utils.ConversionResult;

public class AutomatedSpec extends AbstractViewSpec {
    private ConversionResult result;
    private Map<String, ViewSpecItem.TYPES> components;
    
    public AutomatedSpec(ConversionResult result) {
        this.result = result;
        components = new HashMap<>();
        for (ViewSpecItem.TYPES type : ViewSpecItem.TYPES.values())
            components.put(type.toString(), type);
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        String name, parent, type;
        
        for (ConversionResult specitem : result.getList("views.view.spec")) {
            name = specitem.getst("views.view.spec.item.name");
            parent = specitem.getst("views.view.spec.item.parent");
            type = specitem.getst("views.view.spec.item.type");
            if (parent == null)
                parent = this.parent;
            component(components.get(type), parent, name);
        }
    }
    
}
