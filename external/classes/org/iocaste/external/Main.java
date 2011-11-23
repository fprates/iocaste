package org.iocaste.external;

import com.sun.jna.Native;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void action(ControlData controldata, ViewData view) {
        
    }

    /**
     * 
     * @param view
     * @return
     */
    public static native ExternalContainer get_container(ExternalViewData view);
    
    /**
     * 
     * @param view
     */
    public void main(ViewData view) {
        Container container;
        ExternalContainer econtainer;
        ExternalViewData eview = new ExternalViewData();
        String path = getRealPath("WEB-INF/external/");
        String program = (String)view.getParameter("program");
        
        eview.page = (String)view.getParameter("page");
        
        System.setProperty("jna.library.path", path);
        Native.register(program);
        econtainer = get_container(eview);
        
        container = rebuildContainer(null, econtainer);
        
        view.addContainer(container);
    }
    
    /**
     * 
     * @param master
     * @param econtainer
     * @return
     */
    private Container rebuildContainer(Container master,
            ExternalContainer econtainer) {
        Container container;
        String name = econtainer.base.name;
        Const type = Const.valueOf(econtainer.base.type);
        
        switch (type) {
        case STANDARD_CONTAINER:
            container = new StandardContainer(master, name);
            
            break;
            
        case FORM:
            container = new Form(master, name);
            break;
            
        default:
            return null;
        }
        
        for (ExternalElement eelement: econtainer.elements) {
            name = eelement.name;
            
            if (eelement.iscontainable)
                rebuildContainer(container, (ExternalContainer) 
                        eelement.child.getTypedValue(ExternalContainer.class));
            else
                rebuildElement(container, eelement);
        }
        
        return container;
    }
    
    /**
     * 
     * @param master
     * @param eelement
     * @return
     */
    private Element rebuildElement(Container master,
            ExternalElement eelement) {
        Const type = Const.valueOf(eelement.type);
        String name = eelement.name;
        
        switch (type) {
        case TEXT:
            return new Text(master, name);
        
        case TEXT_FIELD:
            return new TextField(master, name);
            
        default:
            return null;
        }
    }
}
