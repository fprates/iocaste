package org.iocaste.shell.common;

public class MenuItem extends AbstractComponent {
    private static final long serialVersionUID = -222037942601433072L;
    private String text;
    private String function;
    
    public MenuItem(Menu menu, String text, String function) {
        super(menu, Const.MENU_ITEM, function);
        this.text = text;
        this.function = function;
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

}
