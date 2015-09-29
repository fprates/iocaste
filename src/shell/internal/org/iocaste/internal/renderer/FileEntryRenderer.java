package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.InputComponent;

public class FileEntryRenderer extends Renderer {

    public static final XMLElement render(DataItem item) {
        return _render(item);
    }

    public static final XMLElement render(FileEntry file) {
        return _render(file);
    }
    
    /**
     * 
     * @param file
     * @return
     */
    private static final XMLElement _render(InputComponent input) {
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
