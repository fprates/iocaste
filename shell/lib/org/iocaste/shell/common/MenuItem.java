package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

public class MenuItem extends AbstractComponent {
    private static final long serialVersionUID = -222037942601433072L;
    private String text;
    private String function;
    private Map<String, String> parameters;
    
    public MenuItem(Menu menu, String text, String function) {
        super(menu, Const.MENU_ITEM, function);
        this.text = text;
        this.function = function;
        parameters = new HashMap<String, String>();
    }

    /**
     * 
     * @return
     */
    public final String getFunction() {
        return function;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getParameter(String name) {
        return parameters.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final String getText() {
        return text;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }

    /**
     * 
     * @param name
     * @param value
     */
    public final void putParameter(String name, String value) {
        Menu menu = (Menu)getContainer();
        
        if (parameters.containsKey(name))
            parameters.remove(name);
        else
            if (!menu.hasParameter(name)) {
                new Parameter(menu, name);
                menu.addParameter(name);
            }
        
        parameters.put(name, value);
    }
}
