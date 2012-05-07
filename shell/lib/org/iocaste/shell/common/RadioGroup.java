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
    
    public final int add(RadioButton rb) {
        int index = group.size();
        
        group.add(rb);
        
        return index;
    }
    
    public final RadioButton[] getComponents() {
        return group.toArray(new RadioButton[0]);
    }
    
    public final String getName() {
        return name;
    }

}
