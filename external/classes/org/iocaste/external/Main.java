package org.iocaste.external;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private String fileseparator;
    private String path;
    
    public Main() {
        fileseparator = System.getProperty("file.separator");
        path = new StringBuilder(System.getProperty("user.home")).
                append(fileseparator).append("iocaste-external").toString();
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
     * @param path
     * @param view
     * @return
     */
    private final ExternalInterface initExternalProgram(String connector) {
        return (ExternalInterface)Native.loadLibrary(
                connector, ExternalInterface.class);
    }
    
    /**
     * 
     * @param view
     */
    public void main(ViewData view) {
//        Pointer econtainer;
        String connector = convertLibName(path, "iocaste-connector");
        ExternalInterface external = initExternalProgram(connector);
        String progname = (String)view.getParameter("program");
        String progpath = convertLibName(path, progname);
        String page = (String)view.getParameter("page");
        Pointer eprogram = external.icst_ini_program(
                progname, progpath, connector);
        Pointer eview = external.icst_ini_view(eprogram, page);
        
//        for (int k = 0; k < external.icst_get_container_count(eview); k++) {
//            econtainer = external.get_container(k);
//            view.addContainer(rebuildContainer(null, econtainer));
//        }
    }
    
    /**
     * 
     * @param master
     * @param econtainer
     * @return
     */
//    private Container rebuildContainer(Container master, Pointer econtainer) {
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
//        return null;
//    }
    
    /**
     * 
     * @param master
     * @param eelement
     * @return
     */
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
