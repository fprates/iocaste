package org.iocaste.shell.common;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implementação de grupo para radio button.
 * 
 * Agrupa diferentes radio buttons.
 * 
 * @author francisco.prates
 *
 */
public class RadioGroup extends AbstractInputComponent {
    private static final long serialVersionUID = -1996756052092584844L;
    private Set<String> items;
    
    public RadioGroup(View view, String name) {
        super(view, Const.RADIO_GROUP, null, name);
        init();
    }
    
    public RadioGroup(Container container, String name) {
        super(container, Const.RADIO_GROUP, null, name);
        init();
    }
    
    public final RadioButton button(Container container, String name) {
        items.add(name);
        return new RadioButton(this, container, name, items.size() - 1);
    }
    
    public final Set<InputComponent> getComponents() {
        View view = getView();
        Set<InputComponent> components = new HashSet<>();
        
        for (String name : items)
            components.add((InputComponent)view.getElement(name));
        
        return components;
    }
    
    public final RadioButton getSelected() {
        RadioButton rb;
        View view = getView();
        
        for (String name : items) {
            rb = view.getElement(name);
            if (rb.isSelected())
                return rb;
        }
        
        return null;
    }
    
    private final void init() {
        items = new LinkedHashSet<>();
    }
    
    public final void rename(String to, String from) {
        if (!items.contains(from))
            return;
        items.remove(from);
        items.add(to);
    }
    
    @Override
    public void set(Object value) {
        InputComponent input;
        String iname = (String)value;
        View view = getView();
        
        for (String name : items) {
            input = view.getElement(name);
            input.setSelected(name.equals(iname));
        }
    }
}
