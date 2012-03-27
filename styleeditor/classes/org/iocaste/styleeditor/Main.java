package org.iocaste.styleeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public final InstallData install(Message message) {
        InstallData data = new InstallData();
        
        data.link("STYLE", "iocaste-styleeditor");
        
        return data;
    }
    
    public final void main(ViewData view) throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        DataItem item = new DataItem(form, Const.TEXT_FIELD, "estilo");
        
        item.setModelItem(new Documents(this).getModel("STYLE").
                getModelItem("NAME"));
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle("style-editor");
    }
}
