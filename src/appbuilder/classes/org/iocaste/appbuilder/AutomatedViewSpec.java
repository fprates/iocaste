package org.iocaste.appbuilder;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class AutomatedViewSpec extends AbstractViewSpec {
    private List<String[]> instructions;
    
    public AutomatedViewSpec() {
        instructions = new ArrayList<>();
    }
    
    public final void add(String[] instruction) {
        instructions.add(instruction);
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        int index;
        String[] args;
        ViewSpecItem.TYPES[] types = ViewSpecItem.TYPES.values();
        
        for (String[] instruction : instructions) {
            index = Integer.parseInt(instruction[1]);
            args = instruction[0].split("\\.");
            switch (types[index]) {
            case FORM:
                form(args[args.length - 1]);
                break;
            case PAGE_CONTROL:
                navcontrol(args[args.length - 2]);
                break;
            }
        }
    }

}
