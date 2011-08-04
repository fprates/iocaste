package org.iocaste.office;

import org.iocaste.office.common.Office;
import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractForm {
    private Form form;
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#buildViews()
     */
    @Override
    protected void buildViews() {
        ViewData view = getViewInstance("main");
        Container main = new StandardContainer(null);
        
        form = new Form(main, "header");
        form.addAction("send");
        
        new FormItem(form, "receiver", Const.TEXT_FIELD);
        new FormItem(form, "subject", Const.TEXT_FIELD);
        
        view.setContainer(main);
    }
    
    public void send() throws Exception {
        OfficeMessage message = new OfficeMessage();
        Office office = new Office(this);
        
        form.exportTo(message);
        
        office.sendMessage(message);
    }
}
