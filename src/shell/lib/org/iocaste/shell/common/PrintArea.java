package org.iocaste.shell.common;

public class PrintArea extends AbstractInputComponent {
    private static final long serialVersionUID = -3158475553064451862L;
    
    public PrintArea(Container container, String name) {
        super(container, Const.PRINT_AREA, null, name);
    }
    
    public final void add(String line) {
        String value;
        
        if (line == null)
            return;
        
        value = getst();
        if (value == null)
            set(line.concat("\n"));
        else
            set(new StringBuilder(value).append(line).append("\n").toString());
    }

}
