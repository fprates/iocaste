package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.ViewData;

public class Show {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        DocumentModel model;
        String name = ((DataItem)view.getElement("modelname")).getValue();
        Documents documents = new Documents(function);
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            if (!documents.hasModel(name)) {
                view.message(Const.ERROR, "model.not.found");
                return;
            }
            
            model = documents.getModel(name);
            view.export("model", model);
            view.redirect(null, "tbstructure");
            
            break;
            
        case Common.SH:
            view.redirect(null, "shstructure");
            view.export("shname", name);
            
            break;
        }
        
        view.setReloadableView(true);
        view.export("mode", Common.SHOW);
    }
}
