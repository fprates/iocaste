package org.iocaste.dataview;

import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.ReportTool;
import org.iocaste.shell.common.View;

public class Response {

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(View view, Context context) {
        InputComponent input;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "model");
        
        pagecontrol.add("home");
        pagecontrol.add("select", PageControl.REQUEST);
        
        form.importModel(context.modelmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setVisible(true);
        view.setFocus(input);
        view.setTitle("dataview-selection");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @param model
     * @param itens
     * @param viewtype
     */
    public static final void list(View view, Context context) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        ReportTool reporttool = new ReportTool(container, "items");
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
        reporttool.setItens(context.items);
        view.setTitle(context.model.getName());
    }

}
