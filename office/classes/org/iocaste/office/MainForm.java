package org.iocaste.office;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractForm {
    private Form form;
    
    public MainForm() {
        ViewData view = new ViewData();
        Container main = new StandardContainer(null);
        
        form = new Form(main, "header");
        form.addAction("send");
        
        new FormItem(form, "to", Const.TEXT_FIELD);
        new FormItem(form, "subject", Const.TEXT_FIELD);
        
        
        view.setContainer(main);
        
        addView("main", view);
    }
    
    public void send() {
        System.out.println("office message");
//        OfficeMessage message;
//        
//        if (action.equals("send"))
//            saveMessage()
    }
}
