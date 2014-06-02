package org.iocaste.appbuilder.common;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public abstract class AbstractInstallObject {
    private StandardInstallContext context;
    
    protected final void elementchar(String name, int length, boolean upcase) {
        DataElement element = new DataElement(name);
        element.setType(DataType.CHAR);
        element.setLength(length);
        element.setUpcase(upcase);
        context.put(name, element);
    }
    
    protected final void elementdate(String name) {
        DataElement element = new DataElement(name);
        element.setType(DataType.DATE);
        context.put(name, element);
    }
    
    protected final void elementdec(String name, int length, int decimals) {
        DataElement element = new DataElement(name);
        element.setType(DataType.DEC);
        element.setLength(length);
        element.setDecimals(decimals);
        context.put(name, element);
    }
    
    protected final void elementnumc(String name, int length) {
        DataElement element = new DataElement(name);
        
        element.setType(DataType.NUMC);
        element.setLength(length);
        context.put(name, element);
    }
    
    protected abstract void execute(StandardInstallContext context);
    
    protected final ModelInstall modelInstance(String name) {
        return modelInstance(name, null);
    }
    
    protected final ModelInstall modelInstance(String name, String table) {
        ModelInstall model = new ModelInstall(
                context.getInstallData(), name, table);
        model.setElements(context.getElements());
        return model;
    }
    
    public final void run(StandardInstallContext context) {
        this.context = context;
        execute(context);
    }
}
