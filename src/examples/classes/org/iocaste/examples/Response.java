package org.iocaste.examples;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Response {

    public static final void main(View view) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        
        new Button(container, "report");
    }
    
    public static final void ping(View view) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        pagecontrol.add("pinggo", PageControl.REQUEST);
    }
}
