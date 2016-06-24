package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.utils.ConversionResult;

public class AutomatedConfig extends AbstractViewConfig {
    private ConversionResult result;

    public AutomatedConfig(ConversionResult result) {
        this.result = result;
    }

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }
    
}
