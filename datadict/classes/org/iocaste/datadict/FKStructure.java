package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class FKStructure {

    public static final void main(ViewData view, Function function)
            throws Exception {
        InputComponent input;
        DataItem dataitem;
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "fkform");
        Documents documents = new Documents(function);
        TableItem item = (TableItem)view.getParameter("item");
        
        form.importModel(documents.getModel("FOREIGN_KEY"));
        input = (InputComponent)item.get("item.name");
        
        dataitem = form.get("NAME");
        dataitem.setValue(input.getValue());
        dataitem.setEnabled(false);
        
        new Button(container, "fkupdate");
        
        view.addContainer(container);
        view.setNavbarActionEnabled("back", true);
        view.setTitle("fk-editor");
    }
}
