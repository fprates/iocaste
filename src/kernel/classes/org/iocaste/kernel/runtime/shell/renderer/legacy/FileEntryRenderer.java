package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.handlers.OnFocusHandler;

public class FileEntryRenderer extends AbstractElementRenderer<InputComponent> {

    public FileEntryRenderer(HtmlRenderer renderers) {
        super(renderers, Const.FILE_ENTRY);
    }
    
    @Override
    protected final XMLElement execute(InputComponent input, Config config) {
        ActionEventHandler handler;
        XMLElement filetag = new XMLElement("input");
        String name = input.getHtmlName();
        String style = input.getStyleClass();
        
        filetag.add("type", "file");
        filetag.add("name", name);
        filetag.add("id", name);
        if (style != null)
            filetag.add("class", style);
        
        if (!input.isEnabled())
            filetag.add("disabled", "disabled");
        
        handler = config.viewctx.getEventHandler(name, name, "focus");
        handler.name = name;
        handler.call = Shell.send(name, "&event=focus");
        input.put("focus", new OnFocusHandler(input));
        
        return filetag;
    }
}
