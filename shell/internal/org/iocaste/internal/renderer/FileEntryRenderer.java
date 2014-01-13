package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.FileEntry;

public class FileEntryRenderer extends Renderer {

    /**
     * 
     * @param file
     * @return
     */
    public static final XMLElement render(FileEntry file) {
        XMLElement filetag = new XMLElement("input");
        String name = file.getHtmlName();
        String style = file.getStyleClass();
        
        filetag.add("type", "file");
        filetag.add("name", name);
        filetag.add("id", name);
        if (style != null)
            filetag.add("class", style);
        
        if (!file.isEnabled())
            filetag.add("disabled", "disabled");
        
        return filetag;
    }
}
