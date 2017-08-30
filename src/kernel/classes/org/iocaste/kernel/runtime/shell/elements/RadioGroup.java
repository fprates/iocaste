package org.iocaste.kernel.runtime.shell.elements;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.View;

/**
 * Implementação de grupo para radio button.
 * 
 * Agrupa diferentes radio buttons.
 * 
 * @author francisco.prates
 *
 */
public class RadioGroup extends ToolDataElement {
	private static final long serialVersionUID = -6289110111087544467L;
	private Set<String> items;
    private String buttonstyle;
    
    public RadioGroup(ViewContext viewctx, String name) {
        super(viewctx, Const.RADIO_GROUP, name);
        items = new LinkedHashSet<>();
        buttonstyle = "radio_button";
    }
    
    public final RadioButton button(String name) {
        items.add(name);
        return new RadioButton(viewctx, this, name, items.size() - 1);
    }
    
    public final Set<InputComponent> getComponents() {
        View view = getView();
        Set<InputComponent> components = new HashSet<>();
        
        for (String name : items)
            components.add((InputComponent)view.getElement(name));
        
        return components;
    }
    
    public final String getButtonStyleClass() {
        return buttonstyle;
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
    
    @Override
    public final boolean isDataStorable() {
        return true;
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
            if (input.isSelected())
                super.set(value);
        }
    }
    
    public final void setButtonStyleClass(String styleclass) {
        View view = getView();
        buttonstyle = styleclass;
        for (String name : items)
            ((RadioButton)view.getElement(name)).setStyleClass(styleclass);
    }
}
