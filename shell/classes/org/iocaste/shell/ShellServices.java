package org.iocaste.shell;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.TaskEntry;

public class ShellServices extends AbstractFunction {
    public ShellServices() {
        export("get_app_url", "getAppUrl");
        export("get_task_entries", "getTaskEntries");
        addQuery("tasks", "from Task");
    }
    
    /**
     * Retorna url da aplicação informada
     * @param appname nome da aplicação
     * @return url
     */
    public final String getAppUrl(Message message) {
        Task task = (Task)load(Task.class, message.getString("app_name"));
        
        return task.getUrl().trim();
    }
    
    public final List<TaskEntry> getTaskEntries(Message message) {
        Task task;
        List<TaskEntry> tasks = new ArrayList<TaskEntry>();
        List<?> list = select("tasks", null);
        
        for (Object object : list) {
            task = (Task)object;
            tasks.add(new TaskEntry(
                    task.getUrl().trim(), task.getAppName().trim()));
        }
        
        return tasks;
    }

}
