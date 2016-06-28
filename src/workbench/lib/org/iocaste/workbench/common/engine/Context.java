package org.iocaste.workbench.common.engine;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.workbench.common.engine.handlers.ConfigContext;
import org.iocaste.workbench.common.engine.handlers.DataFormConfigHandler;
import org.iocaste.workbench.common.engine.handlers.SpecContext;

public class Context extends AbstractExtendedContext {
    public ConfigContext config;
    public SpecContext spec;
    public Map<String, ConversionResult> views;
    
    public Context(PageBuilderContext context) {
        super(context);
        spec = new SpecContext();
        config = new ConfigContext();
        views = new HashMap<>();
        new DataFormConfigHandler(this);
    }
}
