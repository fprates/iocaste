package org.iocaste.calendar;

import java.util.Date;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    /**
     * 
     * @param controldata
     * @param view
     */
    @Override
    public void back() {
        String position;
        PageStackItem entry;
        
        entry = new Shell(this).popPage();
        position = new StringBuilder(entry.getApp()).append(".").
                append(entry.getPage()).toString();
        backTo(position);
        setReloadableView(false);
    }
    
    @Override
    public final AbstractContext init(View view) {
        Messages messages;
        
        context = new Context();
        messages = new Messages();
        messages.setContext(context);
        messages.entries();
        return context;
    }
    
    public void main() throws Exception {
        Calendar calendar;
        byte mode;
        View view;
        
        context.control = context.function.getParameter("control");
        mode = context.control.getMode();
        view = context.control.getView();
        
        switch (mode) {
        case -1:
        case 1:
            context.control = view.getElement(context.control.getMaster());
            context.date = context.calendardata.calculate(
                    context.control.getDate(), mode);
            break;
        default:
            context.date = new Date();
            break;
        }
        
        new Parameter(context.view, "p_".concat(context.control.getName())).set(
                context.date);
        
        calendar = view.getElement(context.control.getEarly());
        new Parameter(context.view, "p_".concat(calendar.getName()));
        
        calendar = view.getElement(context.control.getLate());
        new Parameter(context.view, "p_".concat(calendar.getName()));
        
        Response.main(context);
    }
}
