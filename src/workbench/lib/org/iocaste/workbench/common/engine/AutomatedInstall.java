package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class AutomatedInstall extends AbstractInstallObject {
    private String content;
    
    public AutomatedInstall(byte[] buffer) {
        content = new String(buffer);
    }
    
    @Override
    protected void execute(StandardInstallContext context) {
        String[] args;
        String[] lines = content.split("\n");
        
        for (String line : lines) {
            args = line.split(":");
            switch (Integer.parseInt(args[1])) {
            case 200:
                context.getInstallData().link(args[0], args[2]);
                break;
            }
        }
    }

}
