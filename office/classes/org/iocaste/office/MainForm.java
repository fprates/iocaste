package org.iocaste.office;

import org.iocaste.office.common.Office;
import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractForm {
    
    public void main(ViewData view) {
        Form form = new Form(null, "header");
        
        form.addAction("send");
        
        new FormItem(form, Const.TEXT_FIELD, "receiver");
        new FormItem(form, Const.TEXT_FIELD, "subject");
        
        view.setContainer(form);
    }
    
    public void send(ControlData controldata, ViewData view) throws Exception {
        Form form = (Form)view.getElement("header");
        OfficeMessage message = new OfficeMessage();
        Office office = new Office(this);
        
        form.exportTo(message);
        
        office.sendMessage(message);
    }
}
