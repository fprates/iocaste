package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de grupo para radio button.
 * 
 * Agrupa diferentes radio buttons.
 * 
 * @author francisco.prates
 *
 */
public class RadioGroup implements Serializable {
    private static final long serialVersionUID = -1996756052092584844L;
    private String name;
    private List<RadioButton> group;
    
    public RadioGroup(String name) {
        this.name = name;
        group = new ArrayList<RadioButton>();
    }
    
    /**
     * Adiciona radio button.
     * @param rb
     * @return
     */
    public final int add(RadioButton rb) {
        int index = group.size();
        
        group.add(rb);
        
        return index;
    }
    
    /**
     * Retorna radio buttons associados.
     * @return array de radio buttons.
     */
    public final RadioButton[] getComponents() {
        return group.toArray(new RadioButton[0]);
    }
    
    /**
     * Retorna nome do grupo.
     * @return nome.
     */
    public final String getName() {
        return name;
    }

}
