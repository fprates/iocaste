package org.iocaste.tasksel;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Install {
    private static final boolean IS_KEY = true;
    
    public static final void main(ViewData view) {
        Container container = new Form(null, "main");
        Text message = new Text(container, "install.continue");
        
        message.setText("Do you want to install this package?");
        new Button(container, "installok");
        new Button(container, "installcancel");
        
        view.setTitle("package.install.confirmation");
        view.addContainer(container);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void proceed(ViewData view, Function function)
            throws Exception {
        String name = view.getParameter("package");
        
        new PackageTool(function).install(name);
        
        view.message(Const.STATUS, "package.installed.successfully");
    }
    
    /**
     * 
     * @return
     */
    public static final InstallData self() {
        DataElement element;
        DocumentModel tasks;
        InstallData data = new InstallData();
        
        tasks = data.getModel("TASKS", "TASKS", "");

        element = data.getDataElement("TASKS.NAME", 0, 18, DataType.CHAR,
                DataType.UPPERCASE);
        
        data.addModelItem(tasks, "NAME", "TSKNM", element, "name", IS_KEY);
        
        element = data.getDataElement("TASKS.COMMAND", 0, 128, DataType.CHAR,
                DataType.KEEPCASE);
        
        data.addModelItem(tasks, "COMMAND", "CMDLN", element, "command", false);
        
        data.addValues(tasks, "DATADICT", "iocaste-datadict");
        data.addValues(tasks, "SE11", "iocaste-datadict");
        data.addValues(tasks, "DATAVIEW", "iocaste-dataview");
        data.addValues(tasks, "SE16", "iocaste-dataview");
        data.addValues(tasks, "INFOSIS", "iocaste-infosis");
        data.addValues(tasks, "TRANSPORT", "iocaste-transport");
        data.addValues(tasks, "EXTERNAL", "iocaste-external");
        
        return data;
    }
}

