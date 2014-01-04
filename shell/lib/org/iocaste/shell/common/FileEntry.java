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
    private int error;
    private byte[] content;
    
    public FileEntry(Container container, String name) {
        super(container, Const.FILE_ENTRY, null, name);
        
        error = 0;
    }

    @Override
    public final byte[] getContent() {
        return content;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.MultipartElement#getError()
     */
    @Override
    public final int getError() {
        return error;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractElement#hasMultipartSupport()
     */
    @Override
    public final boolean hasMultipartSupport() {
        return true;
    }
    
    @Override
    public final void setContent(byte[] content) {
        this.content = content;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.MultipartElement#setError(int)
     */
    @Override
    public final void setError(int error) {
        this.error = error;
    }
}
