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
        
        filetag.add("type", "file");
        filetag.add("name", name);
        filetag.add("id", name);
        filetag.add("value", (String)file.get());
        return filetag;
    }
}
