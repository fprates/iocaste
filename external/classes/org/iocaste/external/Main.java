package org.iocaste.external;

import com.sun.jna.Native;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private String fileseparator;
    
    public Main() {
        fileseparator = System.getProperty("file.separator");
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void action(ControlData controldata, ViewData view) {
        
    }
    
    /**
     * 
     * @param libname
     * @return
     */
    private String convertLibName(String path, String libname) {
        String[] args = System.getProperty("os.name").split("\\s");
        StringBuilder sb = new StringBuilder(path).append(fileseparator);
        
        if (args[0].toUpperCase().equals("WINDOWS"))
            return sb.append(libname).append(".dll").toString();
        
        return sb.append("lib").append(libname).append(".so").toString();
    }
    
    /**
     * 
     * @param view
     */
    public void main(ViewData view) {
//        Container container;
        ExternalInterface external;
//        ExternalContainer econtainer;
        ExternalViewData eview;
        String path = new StringBuilder(System.getProperty("user.home")).
                append(fileseparator).append("iocaste-external").toString();
        String program = convertLibName(path,
                (String)view.getParameter("program"));
        
        external = (ExternalInterface)Native.
                loadLibrary(program, ExternalInterface.class);
        eview = external.init_view((String)view.getParameter("page"), path);
        
        
        eview = null;
        external = null;
//        
//        container = rebuildContainer(null, econtainer);
//        
//        view.addContainer(container);
    }
//    
//    /**
//     * 
//     * @param master
//     * @param econtainer
//     * @return
//     */
//    private Container rebuildContainer(Container master,
//            ExternalContainer econtainer) {
//        Container container;
//        String name = econtainer.base.name;
//        Const type = Const.valueOf(econtainer.base.type);
//        
//        switch (type) {
//        case STANDARD_CONTAINER:
//            container = new StandardContainer(master, name);
//            
//            break;
//            
//        case FORM:
//            container = new Form(master, name);
//            break;
//            
//        default:
//            return null;
//        }
//        
//        for (ExternalElement eelement: econtainer.elements) {
//            name = eelement.name;
//            
//            if (eelement.iscontainable)
//                rebuildContainer(container, (ExternalContainer) 
//                        eelement.child.getTypedValue(ExternalContainer.class));
//            else
//                rebuildElement(container, eelement);
//        }
//        
//        return container;
//    }
//    
//    /**
//     * 
//     * @param master
//     * @param eelement
//     * @return
//     */
//    private Element rebuildElement(Container master,
//            ExternalElement eelement) {
//        Const type = Const.valueOf(eelement.type);
//        String name = eelement.name;
//        
//        switch (type) {
//        case TEXT:
//            return new Text(master, name);
//        
//        case TEXT_FIELD:
//            return new TextField(master, name);
//            
//        default:
//            return null;
//        }
//    }
}
