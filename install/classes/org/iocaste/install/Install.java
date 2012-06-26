package org.iocaste.install;

import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.EventAware;
import org.iocaste.shell.common.View;

public class Install extends AbstractFunction {
    private Welcome welcome;
    private DBConfig dbconfig;
    private Finish finish;
    
    public Install() {
        welcome = new Welcome();
        dbconfig = new DBConfig();
        finish = new Finish();
        
        export("exec_action", "execAction");
        export("get_view_data", "getViewData");
    }
    
    /**
     * Executa métodos associados à action.
     * @param message
     * @return
     * @throws Exception
     */
    public final View execAction(Message message) throws Exception {
        View view = message.get("view");
        String action, controlname = message.getString("action");
        ControlComponent control = view.getElement(controlname);
        
        /*
         * TODO poderia ser feito algo melhor do que este hardcode?
         */
        if (control != null && control.getType() == Const.SEARCH_HELP) {
            view.export("sh", control);
            view.redirect("iocaste-search-help", "main");
            view.setReloadableView(true);
        } else {
            if (control == null) {
                action = controlname;
            } else {
                action = control.getAction();
                if (control.isEventAware()) {
                    control.onEvent(EventAware.ON_CLICK, action);
                    return view;
                }
            }
            
            switch (Stages.valueOf(view.getPageName())) {
            case WELCOME:
                welcome.action(view);
                break;
            case DBCONFIG:
                dbconfig.action(view);
                break;
            }
        }
        
        return view;
    }
    
    /**
     * Gera uma visão solicitada por redirect().
     * @param message
     * @return
     * @throws Exception
     */
    public final View getViewData(Message message) throws Exception {
        View view;
        String page = message.getString("page");
        String app = message.getString("app");
        Map<String, Object> parameters = message.get("parameters");
        
        /*
         * TODO pode ser movido para o servidor
         */
        if (app == null || page == null)
            throw new IocasteException("page not especified.");
        
        view = new View(app, page);
        
        for (String name : parameters.keySet())
            view.export(name, parameters.get(name));
        
        switch (Stages.valueOf(page)) {
        case WELCOME:
            welcome.render(view);
            break;
        case DBCONFIG:
            dbconfig.render(view);
            break;
        case FINISH:
            finish.render(view);
            break;
        }
        
        return view;
    }
}
