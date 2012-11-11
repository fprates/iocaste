package org.iocaste.tasksel;

import java.util.Map;
import java.util.Set;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Map<String, Set<TaskEntry>> lists;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void grouprun(View view) {
        Request.grouprun(view, this);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#help(
     *     org.iocaste.shell.common.ViewData)
     */
    @Override
    public final void help(View vdata) {
        vdata.setParameter("topic", "tasksel-index");
        vdata.redirect("iocaste-help", "view");
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
    @Override
    protected final void init(View view) {
        lists = Response.init(view, this);
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
    public final void main(View view) {
        Response.main(view, lists, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void run(View view) {
        Request.run(view, this);
    }
}
