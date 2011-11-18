package org.iocaste.shell;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;

public class NavigationBar implements Serializable {
    private static final long serialVersionUID = -3075185426719189481L;
    private Map<String, Link> bar;
    private Container container;
    private Container linkarea;
    private Container statusarea;
    private Text message;
    
    public NavigationBar(Container container) {
        this.container = container;
        bar = new LinkedHashMap<String, Link>();
        linkarea = new StandardContainer(container, "navbar.linkarea");
        statusarea = new StandardContainer(container, "navbar.status");
        message = new Text(container, "navbar.message");
        
        addAction("home", "home", ControlComponent.CANCELLABLE);
        addAction("back", "back", ControlComponent.CANCELLABLE);
        addAction("help", "help", ControlComponent.CANCELLABLE);
    }

    /**
     * 
     * @param name
     * @param action
     */
    public void addAction(String name, String action, boolean cancellable) {
        Link link = new Link(linkarea, name, action);
        
        link.setEnabled(false);
        link.setCancellable(cancellable);
        
        bar.put(name, link);
    }
    
    /**
     * 
     * @param text
     */
    private final String composeMessage(Const msgtype, String message) {
        StringBuilder sb;
        
        switch (msgtype) {
        case WARNING:
            sb = new StringBuilder("Aviso: ");
            break;
            
        case ERROR:
            sb = new StringBuilder("Erro: ");
            break;
            
        default:
            sb = new StringBuilder();
            break;
        }
        
        return sb.append(message).toString();
    }
    
    /**
     * 
     * @param enabled
     * @param active
     */
    public void setEnabled(String name, boolean enabled) {
        bar.get(name).setEnabled(enabled);
    }
    
    /**
     * 
     * @param text
     */
    public void setMessage(Const msgtype, String text) {
        message.setText(composeMessage(msgtype, text));
    }
}
