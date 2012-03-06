package org.iocaste.datadict;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Rename {

    public static final void dialog(ViewData view, Function function)
            throws Exception {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "rename.form");
        DataItem oldname = new DataItem(form, Const.TEXT_FIELD, "oldname");
        DataItem newname = new DataItem(form, Const.TEXT_FIELD, "newname");
        DataElement delement = new Documents(function).
                getDataElement("MODEL.NAME");
        
        oldname.setEnabled(false);
        oldname.setValue((String)view.getParameter("oldname"));
        oldname.setDataElement(delement);
        
        newname.setObligatory(true);
        newname.setDataElement(delement);
        
        new Button(container, "renameok");
        
        view.setFocus("newname");
        view.setNavbarActionEnabled("back", true);
        view.addContainer(container);
    }
    
    public static final void main(ViewData view) {
        DataForm form = (DataForm)view.getElement("modelform");
        String oldname = form.get("modelname").getValue();
        
        view.setReloadableView(true);
        view.export("oldname", oldname);
        view.redirect(null, "renamedialog");
    }
    
    public static final void ok(ViewData view, Function function)
            throws Exception {
        DataForm form = (DataForm)view.getElement("rename.form");
        String oldname = form.get("oldname").getValue();
        String newname = form.get("newname").getValue();
        Documents documents = new Documents(function);
        
        if (documents.hasModel(newname)) {
            view.message(Const.ERROR, "model.has.already.exists");
            return;
        }
        
        documents.renameModel(oldname, newname);
        documents.commit();
        
        view.message(Const.STATUS, "model.renamed.successfully");
        ((AbstractPage)function).back(view);
    }
}
