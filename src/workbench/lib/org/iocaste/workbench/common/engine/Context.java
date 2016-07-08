package org.iocaste.workbench.common.engine;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.workbench.common.engine.handlers.ConfigContext;
import org.iocaste.workbench.common.engine.handlers.ComponentConfigHandler;
import org.iocaste.workbench.common.engine.handlers.DataFormConfigHandler;
import org.iocaste.workbench.common.engine.handlers.InputConfigHandler;
import org.iocaste.workbench.common.engine.handlers.SpecContext;
import org.iocaste.workbench.common.engine.handlers.TableToolConfigHandler;

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
        new ComponentConfigHandler(this, ViewSpecItem.TYPES.BUTTON);
        new ComponentConfigHandler(this, ViewSpecItem.TYPES.LINK);
        new ComponentConfigHandler(this, ViewSpecItem.TYPES.TEXT);
        new InputConfigHandler(this, ViewSpecItem.TYPES.FILE_UPLOAD);
        new InputConfigHandler(this, ViewSpecItem.TYPES.PARAMETER);
        new InputConfigHandler(this, ViewSpecItem.TYPES.TEXT_FIELD);
        new TableToolConfigHandler(this);
    }
}
