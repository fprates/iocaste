package org.iocaste.workbench.shell;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.TextField;
import org.iocaste.workbench.Context;

public class WorkbenchShell {

    public static final void output(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        TextField cmdline;
        
        new TextArea(container, "output").setEnabled(false);
        cmdline = new TextField(container, "cmdline");
        cmdline.setLength(100);
        cmdline.setVisibleLength(100);
        context.view.setFocus(cmdline);
        
        pagecontrol.add("home");
        pagecontrol.add("run", PageControl.SUBMIT);
    }
    
    public static final void run(Context context) throws Exception {
        String[] tokens;
        String output = null;
        InputComponent stdout = context.view.getElement("output");
        InputComponent cmdline = context.view.getElement("cmdline");
        
        if (Shell.isInitial(cmdline))
            return;
        
        tokens = cmdline.get().toString().split("\\s");
        switch (tokens[0]) {
        case "project":
            output = Project.execute(tokens, context);
            break;
        case "package":
            output = Package.execute(tokens, context);
            break;
        case "source":
            output = Source.execute(tokens, context);
            break;
        case "exit":
            ((AbstractPage)context.function).home();
            break;
        default:
            output = "invalid.command";
            break;
        }
        
        cmdline.set(null);
        stdout.set(output);
    }
}
