package org.iocaste.datadict;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Rename {

    /**
     * 
     * @param view
     * @param function
     */
    public static final void dialog(View view, Function function) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "rename.form");
        DataItem oldname = new DataItem(form, Const.TEXT_FIELD, "oldname");
        DataItem newname = new DataItem(form, Const.TEXT_FIELD, "newname");
        DataElement delement = new Documents(function).
                getDataElement("MODEL.NAME");
        
        pagecontrol.add("back");
        oldname.setEnabled(false);
        oldname.set(view.getParameter("oldname"));
        oldname.setDataElement(delement);
        
        newname.setObligatory(true);
        newname.setDataElement(delement);
        
        new Button(container, "renameok");
        view.setFocus(newname);
    }
    
    /**
     * 
     * @param view
     */
    public static final void main(View view) {
        DataForm form = view.getElement("modelform");
        String oldname = form.get("modelname").get();
        
        view.export("oldname", oldname);
        view.redirect(null, "renamedialog");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void ok(View view, Function function) {
        DataForm form = view.getElement("rename.form");
        String oldname = form.get("oldname").get();
        String newname = form.get("newname").get();
        Documents documents = new Documents(function);
        
        if (documents.getModel(newname) != null) {
            view.message(Const.ERROR, "model.has.already.exists");
            return;
        }
        
        documents.renameModel(oldname, newname);
        view.message(Const.STATUS, "model.renamed.successfully");
        ((AbstractPage)function).back(view);
    }
}
