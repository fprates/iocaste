package org.iocaste.office;

import org.iocaste.office.common.Office;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractPage {
    
    public void main(ViewData view) {
        Container form = new Form(null, "main");
        DataForm headerform = new DataForm(form, "header");
        
        headerform.addAction("send");
        
        new DataItem(headerform, Const.TEXT_FIELD, "receiver");
        new DataItem(headerform, Const.TEXT_FIELD, "subject");
        
        view.addContainer(form);
    }
    
    public void send(ControlData controldata, ViewData view) throws Exception {
        DataForm form = (DataForm)view.getElement("header");
        OfficeMessage message = new OfficeMessage();
        Office office = new Office(this);
        
        form.exportTo(message);
        
        office.sendMessage(message);
    }
}
