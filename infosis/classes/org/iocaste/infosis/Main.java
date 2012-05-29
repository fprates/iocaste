package org.iocaste.infosis;

import java.util.Properties;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void jvpropin(ViewData view) {
        view.redirect(null, "jvpropout");
    }
    
    public final void jvpropout(ViewData view) {
        Properties properties = System.getProperties();
        Container container = new StandardContainer(view, "container");
        
        report(container, properties);
        
        view.setTitle("java-properties");
        view.setNavbarActionEnabled("back", true);
    }
    
    public final void main(ViewData view) {
        Link link;
        Container container = new Form(view, "main");
        Table table = new Table(container, "links");
        
        table.setHeader(false);
        table.setMark(false);
        new TableColumn(table, "link");
        
        link = new Link(table, "java-properties", "jvpropin");
        link.setText("java-properties");
        new TableItem(table).add(link);
        
        link = new Link(table, "system-info", "sysinfin");
        link.setText("system-info");
        new TableItem(table).add(link);
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle("infosis");
    }
    
    private final void report(Container container, Properties properties) {
        TableItem item;
        Text text;
        Table table = new Table(container, "properties");
        
        new TableColumn(table, "name");
        new TableColumn(table, "value");
        
        table.setMark(false);
        
        for (Object name : properties.keySet()) {
            item = new TableItem(table);
            text = new Text(table, (String)name);
            text.setText((String)name);
            item.add(text);
            
            text = new Text(table, name+"_value");
            text.setText((String)properties.get(name));
            item.add(text);
        }
        
    }
    
    public final void sysinfin(ViewData view) throws Exception {
        view.redirect(null, "sysinfout");
    }
    
    public final void sysinfout(ViewData view) throws Exception {
        Container container = new Form(view, "main");
        Iocaste iocaste = new Iocaste(this);
        Properties properties = iocaste.getSystemInfo();
        
        report(container, properties);
        
        view.setTitle("system-info");
        view.setNavbarActionEnabled("back", true);
    }
}
