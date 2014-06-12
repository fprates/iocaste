package org.iocaste.workbench.common.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.shell.common.AbstractPage;

public class ApplicationEngine extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) { }

    public static byte[] getApplicationContext(AbstractPage page)
            throws Exception {
        byte[] buffer;
        int size;
        InputStream is;
        String installctx = page.getRealPath("META-INF", "context.txt");
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
        byte[] buffer = getApplicationContext(this);
        
        installObject("auto", new AutomatedInstall(buffer));
    }

}
