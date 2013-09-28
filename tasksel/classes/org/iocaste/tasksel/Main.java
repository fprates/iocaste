package org.iocaste.tasksel;

import java.util.Map;
import java.util.Set;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
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
        context.view.redirect("iocaste-help", "view");
    }
    
    @Override
    public final PageContext init(View view) {
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
    
    /**
     * 
     */
    public final void run() {
        Request.run(context);
    }
}

class Context extends PageContext {
    
}