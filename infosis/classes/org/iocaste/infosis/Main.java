package org.iocaste.infosis;

import java.util.Properties;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void jvpropin(View view) {
        view.redirect(null, "jvpropout");
    }
    
    public final void jvpropout(View view) {
        Properties properties = System.getProperties();
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("back");
        report(container, properties);
        
        view.setTitle("java-properties");
    }
    
    public final void main(View view) {
        Link link;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Table table = new Table(container, "links");
        
        pagecontrol.add("home");
        table.setHeader(false);
        table.setMark(false);
        new TableColumn(table, "link");
        
        link = new Link(table, "java-properties", "jvpropin");
        link.setText("java-properties");
        new TableItem(table).add(link);
        
        link = new Link(table, "system-info", "sysinfin");
        link.setText("system-info");
        new TableItem(table).add(link);
        
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
    
    public final void sysinfin(View view) throws Exception {
        view.redirect(null, "sysinfout");
    }
    
    public final void sysinfout(View view) throws Exception {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Iocaste iocaste = new Iocaste(this);
        Properties properties = iocaste.getSystemInfo();
        
        pagecontrol.add("back");
        report(container, properties);
        
        view.setTitle("system-info");
    }
}
