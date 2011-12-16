package org.iocaste.sh;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public final void cancel(ControlData cdata, ViewData vdata) {
        
    }
    
    public final void choose(ControlData cdata, ViewData vdata) {
        // implemente ações como nesse exemplo
    }
    
    public void main(ViewData vdata) {
        Container container = new Form(null, "main");
        ExtendedObject[] result =
                (ExtendedObject[])vdata.getParameter("result");
        Table table = new Table(container, 0, "search.table");
        
        table.importObject(result);
        
        vdata.disableHead();
        vdata.addContainer(container);
    }
}
