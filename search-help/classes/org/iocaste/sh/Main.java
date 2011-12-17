package org.iocaste.sh;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public final void cancel(ControlData cdata, ViewData vdata) {
        
    }
    
    public final void choose(ControlData cdata, ViewData vdata) {
        // implemente ações como nesse exemplo
    }
    
    private ExtendedObject[] getResultsFrom(SearchHelp sh) throws Exception {
        Documents documents = new Documents(this);
        return documents.select("from "+sh.getModelName(), null);
    }
    
    public void main(ViewData vdata) throws Exception {
        Container container = new Form(null, "main");
        SearchHelp sh = (SearchHelp)vdata.getParameter("sh");
        Table table = new Table(container, 0, "search.table");
        
        table.importObject(getResultsFrom(sh));
        
        vdata.disableHead();
        vdata.addContainer(container);
    }
}
