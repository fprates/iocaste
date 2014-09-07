package org.iocaste.tasksel.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.ViewState;

public class TaskSelector extends AbstractServiceInterface {
    private final static String PATH = "/iocaste-tasksel/view.html";
    
    public TaskSelector(Function function) {
        initService(function, PATH);
    }
    
    public ViewState call(String task) {
        Message message = new Message("task_redirect");
        message.add("task", task);
        return call(message);
    }
    
    public void refresh() {
        call(new Message("refresh"));
    }
}
