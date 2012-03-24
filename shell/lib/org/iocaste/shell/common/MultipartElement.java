package org.iocaste.shell.common;

public interface MultipartElement extends InputComponent {
    public static final int FILE_NOT_FOUND = 1;
    public static final int EMPTY_FILE_NAME = 2;
    
    /**
     * Se o componente suporta conteúdo multipart, retorna
     * o caminho para gravação dos dados.
     * @return caminho para gravação dos dados.
     */
    public abstract String getDestiny();
    
    /**
     * 
     * @return
     */
    public abstract int getError();
    
    /**
     * Se o componente suporta conteúdo multipart, ajusta
     * o caminho para gravação dos dados.
     * @param destiny caminho para gravação de dados.
     */
    public abstract void setDestiny(String destiny);
    
    /**
     * 
     * @param error
     */
    public abstract void setError(int error);

}
