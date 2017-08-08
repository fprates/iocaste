package org.iocaste.runtime.common.portal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.ViewConfig;
import org.iocaste.runtime.common.page.ViewInput;
import org.iocaste.runtime.common.page.ViewSpec;
import org.iocaste.runtime.common.style.ViewConfigStyle;

public class PortalPageContext {
    public ViewSpec spec;
    public ViewConfig config;
    public ViewInput input;
    public ViewConfigStyle style;
    public Map<String, ActionHandler> handlers;
    public AbstractPage page;
    
    public PortalPageContext() {
        handlers = new HashMap<>();
    }
}