package org.iocaste.workbench.common.install;

import org.iocaste.protocol.utils.ConversionResult;

public abstract class AbstractInstallModule implements InstallModule {
    protected ModuleContext modulectx;
    private ConversionResult converted;
    private String tag, item;
    
    public AbstractInstallModule(ModuleContext modulectx, String tag,
            String item) {
        this.tag = tag;
        this.modulectx = modulectx;
        this.item = item;
        modulectx.modules.put(tag, this);
        modulectx.mapping.add(tag, item);
    }
    
    protected abstract void execute(ConversionResult map);
    
    @Override
    public final void run() {
        if (item == null)
            execute(converted);
        else
            for (ConversionResult map : converted.getList(tag))
                execute(map);
    }
    
    @Override
    public final void setConverted(ConversionResult converted) {
        this.converted = converted;
    }
    
}
