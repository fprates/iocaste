package org.iocaste.tasksel;

import java.util.Map;
import java.util.Set;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
        export("task_redirect", "redirect");
    }
    
    /**
     * 
     * @param view
     */
    public final void grouprun() {
        Request.grouprun(context);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#help(
     *     org.iocaste.shell.common.ViewData)
     */
    @Override
    public final void help() {
        context.view.setParameter("topic", "tasksel-index");
        context.view.redirect("iocaste-help", "view", true);
    }
    
    @Override
    public final AbstractContext init(View view) {
        context = new Context(); 
        return context;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init(this);
    }
    
    /**
     * Visão geral de tarefas
     * @param view Visão
     */
    public final void main() {
        Map<String, Set<TaskEntry>> lists = Response.init(context);
        Response.main(lists, context);
    }
    
    public final View redirect(Message message) {
        String task = message.getString("task");
        View view = new View(null, null);
        return (Request.call(view, this, task) == 1)? null : view;
    }
}

class Context extends AbstractContext {
    
}