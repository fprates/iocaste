package org.iocaste.kernel.runtime.shell;

import java.util.List;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.legacy.LegacyHtmlRenderer;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.shell.common.View;

public class ProcessLegacyOutput extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        StringBuilder content;
        List<String> lines;
        Config config = new Config();
        View view = message.get("view");
        
        config.viewctx = new ViewContext(view);
        config.viewctx.types = RuntimeEngine.CONST_TYPES;
        config.viewctx.function = getFunction();
        config.viewctx.viewexport = new ViewExport();
        config.viewctx.viewexport.popupcontrol = message.getst("popupcontrol");
        config.viewctx.viewexport.title = view.getTitle();
        config.logid = message.geti("logid");
        config.sequence = message.getl("sequence");
        config.username = message.getst("username");
        config.viewctx.function = getFunction();
        
        lines = new LegacyHtmlRenderer().run(config);
        content = new StringBuilder();
        for (String line : lines)
        	content.append(line);
        
        return content.toString().getBytes();
    }
    
}
