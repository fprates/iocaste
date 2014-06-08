package org.iocaste.workbench;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;
import org.iocaste.workbench.install.Install;
import org.iocaste.workbench.shell.WorkbenchShell;
import org.iocaste.workbench.shell.source.Editor;

/**
 * 
 * @author francisco.prates
 *
 */
public class OldMain extends AbstractPage {
//    private Context context;
//
//    public OldMain() {
//        export("install", "install");
//    }
//    
//    public final InstallData install(Message message) {
//        return Install.execute();
//    }
//    
    @Override
    protected AbstractContext init(View view) throws Exception {
//        context = new Context();
//        
//        context.repository = Common.composeFileName(
//                System.getProperty("user.dir"),
//                ".iocaste", "workbench");
//                
//        return context;
        return null;
    }
//    
//    public final void main() {
//        WorkbenchShell.output(context);
//    }
//    
//    public final void run() throws Exception {
//        WorkbenchShell.run(context);
//    }
//    
//    public final void save() {
//        Editor.save(context);
//    }
//    
//    public final void source() {
//        Editor.output(context);
//    }
}
