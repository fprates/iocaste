package org.iocaste.infosis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
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
    private List<Map<String, Object>> users;

    public Main() {
        export("install", "install");
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void jvpropin(View view) {
        view.redirect("jvpropout");
    }
    
    public final void jvpropout(View view) {
        Properties properties = System.getProperties();
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("back");
        report(container, properties);
        
        view.setTitle("java-properties");
    }
    
    public final void list(View view) {
        TableItem item;
        Text text;
        Table itens;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);

        pagecontrol.add("back");
        
        new Button(container, "usrsrfrsh");
        itens = new Table(container, "itens");
        
        new TableColumn(itens, "username");
        new TableColumn(itens, "terminal");
        new TableColumn(itens, "begin");
        for (Map<String, Object> user : users) {
            item = new TableItem(itens);
            text = new Text(itens, "username");
            text.setText(user.get("username").toString());
            item.add(text);
            text = new Text(itens, "terminal");
            text.setText(user.get("terminal").toString());
            item.add(text);
            text = new Text(itens, "begin");
            text.setText(user.get("connection.time").toString());
            item.add(text);
        }
        view.setTitle("users-list");
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
        
        link = new Link(table, "users-list", "usrslst");
        link.setText("users-list");
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
    
    public final void sysinfin(View view) {
        view.redirect("sysinfout");
    }
    
    public final void sysinfout(View view) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Iocaste iocaste = new Iocaste(this);
        Properties properties = iocaste.getSystemInfo();
        
        pagecontrol.add("back");
        report(container, properties);
        
        view.setTitle("system-info");
    }
    
    /**
     * 
     * @param view
     */
    public final void usrslst(View view) {
        Iocaste iocaste = new Iocaste(this);
        String[] sessionids = iocaste.getSessions();

        users = new ArrayList<>();
        for (int i = 0; i < sessionids.length; i++)
            users.add(iocaste.getSessionInfo(sessionids[i]));
        
        view.redirect("list");
    }
    
    public final void usrsrfrsh(View view) {
        usrslst(view);
        view.dontPushPage();
    }
}
