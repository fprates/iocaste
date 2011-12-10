package org.iocaste.tasksel;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#help(
     *     org.iocaste.shell.common.ControlData,
     *     org.iocaste.shell.common.ViewData)
     */
    @Override
    public final void help(ControlData cdata, ViewData vdata) {
        cdata.addParameter("topic", "tasksel-index");
        cdata.redirect("iocaste-help", "view");
    }
    
    /**
     * Visão geral de tarefas
     * @param view Visão
     * @throws Exception 
     */
    public final void main(ViewData view) throws Exception {
        Container form = new Form(null, "selector.form");
        DataForm dataform = new DataForm(form, "selector.dataform");
        DataItem dataitem = new DataItem(dataform, Const.TEXT_FIELD, "command");
        
        dataitem.setLength(80);
        dataform.addAction("run");
        
        view.setNavbarActionEnabled("help", true);
        view.setFocus("command");
        view.addContainer(form);
        view.setTitle("task.selector");
    }
    
    /**
     * Controlador geral de tarefas
     * @param controldata
     * @param view
     */
    public final void run(ControlData cdata, ViewData vdata) {
        String[] parsed;
        DataItem dataitem = (DataItem)vdata.getElement("command");
        String command = dataitem.getValue();
        String app = null;
        String page = null;
        
        if (command == null)
            return;
        else
            command.trim();
        
        if (command.length() == 0)
            return;
        
        dataitem.setValue("");
        parsed = command.split("\\s");
        
        for (int i = 0; i < parsed.length; i++) {
            switch (i) {
            case 0:
                app = parsed[i];
                break;
            case 1:
                page = parsed[i];
                break;
            default:
                break;
            }
        }
        
        if (app == null)
            return;
        
        if (page == null) {
            cdata.message(Const.ERROR, "page.missing");
            return;
        }
        
        cdata.redirect(app, page);
    }
}
