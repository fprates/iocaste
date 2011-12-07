package org.iocaste.external;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void action(ControlData controldata, ViewData view) {
        
    }
    
    /**
     * 
     * @param view
     * @throws Exception 
     */
    public void main(ViewData view) throws Exception {
        ExternalViewServiceStub eview = new ExternalViewServiceStub
            ("http://localhost:8080/axis2/services/ExternalViewService");
        GetViewDocument viewdoc = GetViewDocument.Factory.newInstance();
        GetViewResponseDocument response = eview.getView(viewdoc);
        
        System.out.println(response.getGetViewResponse().getReturn().getTitle());
    }
}
