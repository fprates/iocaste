package org.iocaste.tasksel;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#help(
     *     org.iocaste.shell.common.ViewData)
     */
    @Override
    public final void help(ViewData vdata) {
        vdata.addParameter("topic", "tasksel-index");
        vdata.redirect("iocaste-help", "view");
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
        
        dataitem.setLength(255);
        dataform.addAction("run");
        
        view.setNavbarActionEnabled("help", true);
        view.setFocus("command");
        view.addContainer(form);
        view.setTitle("task-selector");
    }
    
    /**
     * Controlador geral de tarefas
     * @param view
     */
    public final void run(ViewData vdata) throws Exception {
        String[] parsed, values;
        Iocaste iocaste;
        DataItem dataitem = (DataItem)vdata.getElement("command");
        String command = dataitem.getValue();
        String page = "main";
        String app = null;
        
        if (command == null)
            return;
        else
            command.trim();
        
        if (command.length() == 0)
            return;
        
        dataitem.setValue("");
        parsed = command.split("\\s");
        vdata.clearParameters();
        
        for (int i = 0; i < parsed.length; i++) {
            switch (i) {
            case 0:
                app = parsed[i];
                break;
            default:
                if (parsed[i].startsWith("@")) {
                    page = parsed[i].substring(1);
                    break;
                }
                
                values = parsed[i].split("=");
                if (values.length < 2)
                    break;
                
                vdata.addParameter(values[0], values[1]);
                break;
            }
        }
        
        if (app == null)
            return;
        
        iocaste = new Iocaste(this);
        if (!iocaste.isAppEnabled(app)) {
            vdata.message(Const.ERROR, "app.not.enabled");
        } else {
            vdata.setReloadableView(true);
            vdata.redirect(app, page);
        }
    }
}
