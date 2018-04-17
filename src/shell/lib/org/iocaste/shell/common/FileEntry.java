package org.iocaste.shell.common;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Componente para upload de arquivos.
 * 
 * @author francisco.prates
 *
 */
public class FileEntry extends ToolDataElement {
    private static final long serialVersionUID = -3285030860250606539L;
    
    public FileEntry(Container container, String name) {
        this(new ElementViewContext(null, container, TYPES.FILE_UPLOAD, name), name);
    }
    
    public FileEntry(Context context, String name) {
        super(context, Const.FILE_ENTRY, name);
        tooldata.multipart = true;
    }
}
