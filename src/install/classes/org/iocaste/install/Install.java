package org.iocaste.install;

import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Install extends AbstractFunction {
    private Context context;
    
    public Install() {        
        export("exec_action", "execAction");
        export("get_view_data", "getViewData");
        context = new Context();
    }
    
    /**
     * Executa métodos associados à action.
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewState execAction(Message message) throws Exception {
        View view = message.get("view");
        String controlname = message.getString("action");
        ControlComponent control = view.getElement(controlname);
        
        /*
         * TODO poderia ser feito algo melhor do que este hardcode?
         */
        if (control != null && control.getType() == Const.SEARCH_HELP) {
            view.export("sh", control);
            context.state.rapp = "iocaste-search-help";
            context.state.rpage = "main";
        } else {
            context.view = view;
            context.function = this;
            
            switch (Stages.valueOf(view.getPageName())) {
            case WELCOME:
                Welcome.action(view, context.state);
                break;
            case DBCONFIG:
                DBConfig.action(context);
                break;
            default:
                break;
            }
        }
        
        context.state.view = view;
        return context.state;
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
        
        context.view = view;
        context.function = this;
        
        switch (Stages.valueOf(page)) {
        case WELCOME:
            Welcome.render(view);
            break;
        case DBCONFIG:
            DBConfig.render(context);
            break;
        case FINISH:
            Finish.render(view);
            break;
        }
        
        return view;
    }
}
