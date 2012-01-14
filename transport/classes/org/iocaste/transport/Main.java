package org.iocaste.transport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    public final void importing(ViewData view) throws Exception {
        Parameter filepath = (Parameter)view.getElement("file");
        File file = new File(filepath.getValue());
        FileReader freader = new FileReader(file);
        BufferedReader breader = new BufferedReader(freader);
        List<String> list = new ArrayList<String>();
        
        while (breader.ready())
            list.add(breader.readLine());
        
        breader.close();
        
        view.export("list", list.toArray(new String[0]));
        view.redirect(null, "report");
        view.message(Const.STATUS, "objects.deployed.successfully");
        view.setReloadableView(true);
    }
    
    private final String getTransportDir() {
        return getRealPath("../../../transport");
    }

    public void main(ViewData view) {
        Link link;
        String name, path = getTransportDir();
        Container container = new Form(null, "main");
        File files = new File(path);
        Parameter file = new Parameter(container, "file");
        
        for (String filename : files.list()) {
            name = filename.replace(' ', '_');
            link = new Link(container, "import_"+name, filename);
            link.add(file, path+System.getProperty("file.separator")+filename);
            link.setAction("importing");
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle("object-transport-program");
        view.addContainer(container);
    }
    
    public final void report(ViewData view) {
        Container container = new Form(null, "report");
        String[] list = (String[])view.getParameter("list");
        
        for (String line : list)
            view.print(line);
        
        view.setTitle("transport-order-viewer");
        view.setNavbarActionEnabled("back", true);
        view.addContainer(container);
    }
}
