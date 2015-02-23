package org.iocaste.shell.common;

import java.util.Date;

/**
 * Ajuda de pesquisa
 * 
 * Define parâmetros para ajuda de pesquisa.
 * 
 * @author francisco.prates
 *
 */
public class Calendar extends PopupControl {
    private static final long serialVersionUID = -5466384144408033617L;
    public static final byte EARLY = -1;
    public static final byte LATE = 1;
    private String inputname, early, late, master;
    private byte mode;
    private Date date;
    
    public Calendar(Container container, String name) {
        this(container, name, (byte)0);
    }
    
    public Calendar(Container container, String name, byte mode) {
        super(container, Const.CALENDAR, name);
        
        setAllowStacking(true);
        setApplication("iocaste-calendar");
        this.mode = mode;
    }
    
    /**
     * 
     * @return
     */
    public final Date getDate() {
        return date;
    }
    
    /**
     * 
     * @return
     */
    public final String getEarly() {
        return early;
    }
    
    /**
     * Retorna componente associado.
     * @return nome do componente.
     */
    public final String getInputName() {
        return inputname;
    }
    
    /**
     * 
     * @return
     */
    public final String getLate() {
        return late;
    }
    
    /**
     * 
     * @return
     */
    public final String getMaster() {
        return master;
    }
    
    /**
     * 
     * @return
     */
    public final byte getMode() {
        return mode;
    }
    
    /**
     * 
     * @param date
     */
    public final void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * 
     * @param early
     */
    public final void setEarly(String early) {
        this.early = early;
    }
    
    /**
     * Define campo de entrada associado.
     * @param inputname nome do campo.
     */
    public final void setInputName(String inputname) {
        this.inputname = inputname;
    }
    
    /**
     * 
     * @param late
     */
    public final void setLate(String late) {
        this.late = late;
    }
    
    /**
     * 
     * @param master
     */
    public final void setMaster(String master) {
        this.master = master;
    }

    @Override
    public void update(View view) {
        InputComponent input;
        Calendar calendar;
        View _view = getView();
        
        if (master == null) {
            input = view.getElement("p_".concat(getName()));
            date = input.get();
        } else {
            input = view.getElement("p_".concat(master));
            calendar = _view.getElement(master);
            calendar.setDate(input.getdt());
        }
    }
}
