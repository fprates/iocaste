package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
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
        
        return filetag;
    }
}
