package org.iocaste.tasksel;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ViewData;

public class Install {
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void installok(ViewData view, Function function)
            throws Exception {
        String name = view.getParameter("package");
        
        new PackageTool(function).install(name);
        
        view.message(Const.STATUS, "package.installed.successfully");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void removeok(ViewData view, Function function)
            throws Exception {
        String name = view.getParameter("package");
        
        new PackageTool(function).uninstall(name);
        
        view.message(Const.STATUS, "package.uninstalled.successfully");
    }
    
    /**
     * 
     * @return
     */
    public static final InstallData self() {
        DataElement element;
        DocumentModel tasks;
        DocumentModelItem item;
        InstallData data = new InstallData();
        
        tasks = data.getModel("TASKS", "TASKS", "");

        element = new DataElement();
        element.setName("TASKS.NAME");
        element.setLength(18);
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("NAME");
        item.setTableFieldName("TSKNM");
        item.setDataElement(element);
        
        tasks.add(item);
        tasks.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("TASKS.COMMAND");
        element.setLength(128);
        element.setType(DataType.CHAR);
        
        item = new DocumentModelItem();
        item.setName("COMMAND");
        item.setTableFieldName("CMDLN");
        item.setDataElement(element);
        
        tasks.add(item);
        
        data.link("INFOSIS", "iocaste-infosis");
        data.link("TRANSPORT", "iocaste-transport");
        data.link("EXTERNAL", "iocaste-external");
        
        return data;
    }
}

