package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.List;

public class PrintArea extends AbstractInputComponent {
    private static final long serialVersionUID = -3158475553064451862L;
    private List<String> lines;
    
    public PrintArea(Container container, String name) {
        super(container, Const.PRINT_AREA, null, name);
        lines = new ArrayList<>();
    }
    
    public final void add(String line) {
        if (line != null)
            lines.add(line);
    }
    
    public final void clear() {
        lines.clear();
    }
    
    public final void commit() {
        StringBuilder sb = new StringBuilder();
        for (String line : lines)
            sb.append(line).append("\n");
        set(sb.toString());
    }

}
