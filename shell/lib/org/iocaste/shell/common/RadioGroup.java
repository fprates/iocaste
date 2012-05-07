package org.iocaste.shell.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RadioGroup implements Serializable {
    private static final long serialVersionUID = -1996756052092584844L;
    private String name;
    private List<RadioButton> group;
    
    public RadioGroup(String name) {
        this.name = name;
        group = new ArrayList<RadioButton>();
    }
    
    public final void add(RadioButton rb) {
        group.add(rb);
    }
    
    public final InputComponent[] getComponents() {
        return group.toArray(new RadioButton[0]);
    }
    
    public final String getName() {
        return name;
    }

}
