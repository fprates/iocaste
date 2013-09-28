package org.iocaste.datadict;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;

public class Rename {

    public static final void dialog(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "rename.form");
        DataItem oldname = new DataItem(form, Const.TEXT_FIELD, "oldname");
        DataItem newname = new DataItem(form, Const.TEXT_FIELD, "newname");
        DataElement delement = new Documents(context.function).
                getDataElement("MODEL.NAME");
        
        pagecontrol.add("back");
        oldname.setEnabled(false);
        oldname.set(context.oldname);
        oldname.setDataElement(delement);
        
        newname.setObligatory(true);
        newname.setDataElement(delement);
        
        new Button(container, "renameok");
        context.view.setFocus(newname);
    }
    
    public static final void main(Context context) {
        DataForm form = context.view.getElement("model");
        
        context.view.redirect("renamedialog");
        context.oldname = form.get("name").get();
    }
    
    public static final void ok(Context context) {
        DataForm form = context.view.getElement("rename.form");
        String oldname = form.get("oldname").get();
        String newname = form.get("newname").get();
        Documents documents = new Documents(context.function);
        
        if (documents.getModel(newname) != null) {
            context.view.message(Const.ERROR, "model.has.already.exists");
            return;
        }
        
        documents.renameModel(oldname, newname);
        context.view.message(Const.STATUS, "model.renamed.successfully");
        ((AbstractPage)context.function).back();
    }
}
