package org.iocaste.install;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.RadioGroup;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class DBConfigResponse {
    private static final String[] LINES = {
        "Informe usuário e senha para criação do banco de dados do sistema.",
        "Todos os dados anteriores serão destruídos."
    };
    
    public static final void render(View view) {
        Container dbtypecnt;
        DataItem item;
        DataForm dbinfo;
        Form container = new Form(view, "main");
        RadioGroup rbgroup = new RadioGroup("dbtype");
        
        for (int i = 0; i < LINES.length; i++)
            new Text(container, Integer.toString(i)).setText(LINES[i]);
        
        dbinfo = new DataForm(container, "dbinfo");
        item = new DataItem(dbinfo, Const.TEXT_FIELD, "host");
        item.setLength(100);
        item.setObligatory(true);
        view.setFocus(item);
        
        new DataItem(dbinfo, Const.TEXT_FIELD, "username").setObligatory(true);
        new DataItem(dbinfo, Const.TEXT_FIELD, "secret").setSecret(true);
        new DataItem(dbinfo, Const.TEXT_FIELD, "dbname").setObligatory(true);
        
        item = new DataItem(dbinfo, Const.LIST_BOX, "options");
        item.add("keepbase", DBConfig.KEEP_BASE);
        item.add("chngbase", DBConfig.CHANGE_BASE);
        item.add("newbase", DBConfig.NEW_BASE);
        
        dbtypecnt = new StandardContainer(container, "dbtypecnt");
        for (String dbname : DBNames.names.keySet())
            new RadioButton(dbtypecnt, dbname, rbgroup).setText(dbname);
        
        new Button(container, "continue");
        new Parameter(container, "nextstage").set("DBCREATE");
        
        view.setTitle("db-config");
    }
}
    