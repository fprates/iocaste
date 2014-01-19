package org.iocaste.shell.common;

import java.util.HashSet;
import java.util.Set;

public class Menu extends AbstractContainer {
    private static final long serialVersionUID = -7154471704088517956L;
    private String action;
    private Parameter parameter;
    private Set<String> parameters;
    
    public Menu(Container container, String action) {
        super(container, Const.MENU, action);
        this.action = action;
        parameter = new Parameter(this, "function");
        parameters = new HashSet<String>();
    }
    
    public final void addParameter(String name) {
        parameters.add(name);
    }
    
    public final String getAction() {
        return action;
    }
    
    public final Parameter getParameter() {
        return parameter;
    }

    public final MenuItem getSelectedItem() {
        MenuItem menuitem;
        
        for (Element element: getElements()) {
            if (!(element instanceof MenuItem))
                continue;
            
            menuitem = (MenuItem)element;
            if (parameter.getValue().equals(menuitem.getFunction()))
                return menuitem;
        }
        
        return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final boolean hasParameter(String name) {
        return parameters.contains(name);
    }
    
    /**
     * 
     * @param parameter
     */
    public final void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
