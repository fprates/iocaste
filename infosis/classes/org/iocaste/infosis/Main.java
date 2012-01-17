package org.iocaste.infosis;

import java.util.Properties;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public final void main(ViewData vdata) {
        TableItem item;
        Text text;
        Properties properties = System.getProperties();
        Container container = new StandardContainer(null, "container");
        Table table = new Table(container, "properties");
        
        table.addColumn("name");
        table.addColumn("value");
        table.setMark(false);
        
        for (Object name : properties.keySet()) {
            item = new TableItem(table);
            text = new Text(table, name.toString());
            item.add(text);
            text = new Text(table, name+"_value");
            text.setText((String)properties.get(name));
            item.add(text);
        }
        
        vdata.setTitle("iocaste-infosis");
        vdata.setNavbarActionEnabled("back", true);
        vdata.addContainer(container);
    }
}
