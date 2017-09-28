package org.iocaste.shell.common;

import java.util.Date;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;

/**
 * Ajuda de pesquisa
 * 
 * Define parâmetros para ajuda de pesquisa.
 * 
 * @author francisco.prates
 *
 */
public class Calendar extends ToolDataElement {
    private static final long serialVersionUID = -5466384144408033617L;
    public static final byte EARLY = -1;
    public static final byte LATE = 1;
    
    public Calendar(Container container, String name) {
        this(container, name, 0);
    }
    
    public Calendar(Container container, String name, int mode) {
        this(new ElementViewContext(null, container, null, name), name, mode);
    }
    
    public Calendar(Context context, String name, int mode) {
        super(context, Const.CALENDAR, name);
        setAllowStacking(true);
        tooldata.values.put("mode", mode);
    }
    
    /**
     * 
     * @return
     */
    public final Date getDate() {
        return (Date)tooldata.values.get("date");
    }
    
    /**
     * 
     * @return
     */
    public final String getEarly() {
        return (String)tooldata.values.get("early");
    }
    
    /**
     * Retorna componente associado.
     * @return nome do componente.
     */
    public final String getInputName() {
        return (String)tooldata.values.get("inputname");
    }
    
    /**
     * 
     * @return
     */
    public final String getLate() {
        return (String)tooldata.values.get("late");
    }
    
    /**
     * 
     * @return
     */
    public final int getMode() {
        return (int)tooldata.values.get("mode");
    }
    
    @Override
    public boolean isControlComponent() {
        return true;
    }
    
    @Override
    public final boolean isPopup() {
        return true;
    }
    
    @Override
    public final boolean isReady(PopupControl popupcontrol) {
        String calname = getHtmlName();
        Calendar calendar = (Calendar)popupcontrol;
        return ((calname.equals(calendar.getEarly()) ||
                 calname.equals(calendar.getLate()) ||
                 calname.equals(calendar.getName())));
    }
    
    /**
     * 
     * @param date
     */
    public final void setDate(Date date) {
        tooldata.values.put("date", date);
    }
    
    /**
     * 
     * @param early
     */
    public final void setEarly(String early) {
        tooldata.values.put("early", early);
    }
    
    /**
     * Define campo de entrada associado.
     * @param inputname nome do campo.
     */
    public final void setInputName(String inputname) {
        tooldata.values.put("inputname", inputname);
    }
    
    /**
     * 
     * @param late
     */
    public final void setLate(String late) {
        tooldata.values.put("late", late);
    }

    @Override
    public void update(String action, Object value) {
        InputComponent input;
        Calendar calendar;
        String master;
        
        if ((master = getMaster()) == null) {
            input = view.getElement(getInputName());
            input.set(value);
        } else {
            calendar = view.getElement(master);
            calendar.setDate((Date)value);
        }
    }
}
