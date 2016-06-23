package org.iocaste.workbench.common.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.protocol.utils.ConversionResult;

public class DataElementModule extends AbstractInstallModule {
    
    public DataElementModule(ModuleContext context) {
        super(context, "install.elements", "element");
        context.mapping.setType(
                "install.elements.element.name", String.class);
        context.mapping.setType(
                "install.elements.element.type", int.class);
        context.mapping.setType(
                "install.elements.element.length", int.class);
        context.mapping.setType(
                "install.elements.element.upcase", boolean.class);
    }
    
    protected final void execute(ConversionResult map) {
        String name;
        DataElement element;
        int length, type;
        Object upcase;
        
        name = map.getst("install.elements.element.name");
        type = map.geti("install.elements.element.type");
        length = map.geti("install.elements.element.length");
        upcase = map.get("install.elements.element.upcase");
        
        element = new DataElement(name);
        element.setType(type);
        element.setLength(length);
        if (upcase != null)
            element.setUpcase((boolean)upcase);
        modulectx.elements.put(name, element);
    }
}
