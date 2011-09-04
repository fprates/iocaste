package org.iocaste.office;

import org.iocaste.office.common.Office;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataFormItem;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractPage {
    
    public void main(ViewData view) {
        DataForm form = new DataForm(null, "header");
        
        form.addAction("send");
        
        new DataFormItem(form, Const.TEXT_FIELD, "receiver");
        new DataFormItem(form, Const.TEXT_FIELD, "subject");
        
        view.setContainer(form);
    }
    
    public void send(ControlData controldata, ViewData view) throws Exception {
        DataForm form = (DataForm)view.getElement("header");
        OfficeMessage message = new OfficeMessage();
        Office office = new Office(this);
        
        form.exportTo(message);
        
        office.sendMessage(message);
    }
}
