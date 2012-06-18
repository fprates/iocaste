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
    private String destiny;
    private int error;
    
    public FileEntry(Container container, String name) {
        super(container, Const.FILE_ENTRY, null, name);
        
        destiny = null;
        error = 0;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.MultipartElement#getDestiny()
     */
    @Override
    public final String getDestiny() {
        return destiny;
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
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.MultipartElement#setDestiny(
     *      java.lang.String)
     */
    @Override
    public final void setDestiny(String destiny) {
        this.destiny = destiny;
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
