package org.iocaste.examples;

import java.net.URL;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.service.External;
import org.iocaste.external.service.WebService;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Extern {

    public static final void render(View view) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        new Text(container, "info").setText("click to talk to webservice");
        new Button(container, "externsend");
    }
    
    public static final void send(Context context) throws Exception {
        External external = new External(context.function);
        URL url = new URL("http://localhost:8080/axis2/services/Version?wsdl");
        WebService ws = external.getWSData(url,
                "http://localhost:8080/axis2/services/Version");
        ExtendedObject object = external.call(ws, "Version");
    }
}
