package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;

public class FileEntryRenderer extends AbstractElementRenderer<InputComponent> {

    public FileEntryRenderer(HtmlRenderer renderer) {
        super(renderer, Const.FILE_ENTRY);
    }
    
    @Override
    protected final XMLElement execute(InputComponent input, Config config) {
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
        
        return filetag;
    }
}
