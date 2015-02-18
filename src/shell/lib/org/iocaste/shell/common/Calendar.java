package org.iocaste.shell.common;

/**
 * Ajuda de pesquisa
 * 
 * Define parâmetros para ajuda de pesquisa.
 * 
 * @author francisco.prates
 *
 */
public class Calendar extends AbstractControlComponent {
    private static final long serialVersionUID = -5466384144408033617L;
    private String inputname;
    
    public Calendar(Container container, String name) {
        super(container, Const.CALENDAR, name);
        
        setAllowStacking(true);
        setApplication("iocaste-calendar");
    }
    
    /**
     * Retorna componente associado.
     * @return nome do componente.
     */
    public final String getInputName() {
        return inputname;
    }
    
    /**
     * Define campo de entrada associado.
     * @param inputname nome do campo.
     */
    public final void setInputName(String inputname) {
        this.inputname = inputname;
    }
}
