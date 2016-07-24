package org.iocaste.shell.common;

/**
 * Componente para upload de arquivos.
 * 
 * @author francisco.prates
 *
 */
public class FileEntry extends AbstractInputComponent
            implements MultipartElement {
    private static final long serialVersionUID = -3285030860250606539L;
    
    public FileEntry(Container container, String name) {
        super(container, Const.FILE_ENTRY, null, name);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractElement#hasMultipartSupport()
     */
    @Override
    public final boolean hasMultipartSupport() {
        return true;
    }
}
