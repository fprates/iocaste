package org.iocaste.dataview;

import org.iocaste.appbuilder.common.reporttool.ReportTool;
import org.iocaste.appbuilder.common.reporttool.ReportToolData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;

public class Response {

    public static final void main(Context context) {
        InputComponent input;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "model");
        
        pagecontrol.add("home");
        pagecontrol.add("select", PageControl.REQUEST);
        
        form.importModel(context.modelmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setVisible(true);
        input.setObligatory(true);
        context.view.setFocus(input);
        context.view.setTitle("dataview-selection");
    }
    
    public static final void list(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        ReportToolData data;
        
        data = new ReportToolData();
        data.container = container.getHtmlName();
        data.name = "items";
        data.context = context;
        data.objects = context.items;
        data.model = context.model.getName();
        
        new ReportTool(data);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
        context.view.setTitle(context.model.getName());
    }

}
