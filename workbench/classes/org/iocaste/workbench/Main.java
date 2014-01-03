package org.iocaste.workbench;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;
import org.iocaste.workbench.install.Install;
import org.iocaste.workbench.shell.WorkbenchShell;

/**
 * 
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {
    private Context context;

    public Main() {
        export("install", "install");
    }
    
    public final InstallData install(Message message) {
        return Install.execute();
    }
    
    @Override
    protected PageContext init(View view) throws Exception {
        context = new Context();
        
        return context;
    }
    
    public final void main() {
        WorkbenchShell.output(context);
    }
    
    public final void run() {
        WorkbenchShell.run(context);
    }
}
