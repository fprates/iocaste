package org.iocaste.workbench.common.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class ApplicationEngine extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        String view;
        AutomatedViewSpec spec;
        int index;
        ViewSpecItem.TYPES[] types;
        String[] args;
        byte[] buffer = getApplicationContext();
        String[] lines = new String(buffer).split("\n");

        types = ViewSpecItem.TYPES.values();
        spec = null;
        for (String line : lines) {
            args = line.split(":");
            index = Integer.parseInt(args[1]);
            if (index == -1)
                continue;

            if (index >= 200)
                return;
            
            switch (types[index]) {
            case VIEW:
                args = args[0].split("\\.");
                view = args[args.length - 1];
                spec = new AutomatedViewSpec();
                context.setViewSpec(view, spec);
                break;
            default:
                spec.add(args);
                break;
            }
        }
    }

    private byte[] getApplicationContext() throws Exception {
        byte[] buffer;
        int size;
        InputStream is;
        String installctx = getRealPath("META-INF", "context.txt");
        File file = new File(installctx);
        
        size = ((Number)file.length()).intValue();
        buffer = new byte[size];
        is = new FileInputStream(file);
        is.read(buffer);
        is.close();
        
        return buffer;
    }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        byte[] buffer = getApplicationContext();
        
        installObject("auto", new AutomatedInstall(buffer));
    }

}
