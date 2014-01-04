package org.iocaste.shell.common;

/**
 * Interface para componentes multi-part.
 * 
 * A principal implementação é FileEntry.
 * 
 * @author francisco.prates
 *
 */
public interface MultipartElement extends InputComponent {
    public static final int FILE_NOT_FOUND = 1;
    public static final int EMPTY_FILE_NAME = 2;
    
    public abstract byte[] getContent();
    
    /**
     * Retorna código de erro.
     * @return código de erro (veja constantes).
     */
    public abstract int getError();
    
    public abstract void setContent(byte[] content);
    
    /**
     * Ajusta código de erro.
     * @param error código de erro (veja constantes).
     */
    public abstract void setError(int error);

}
