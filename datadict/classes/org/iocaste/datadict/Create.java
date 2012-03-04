package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.ViewData;

public class Create {
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        DocumentModel model;
        Documents documents = new Documents(function);
        String name = ((DataItem)view.getElement("modelname")).getValue();
        int op = Common.getTpObjectValue(view);
        
        switch (op) {
        case Common.TABLE:
            if (documents.hasModel(name)) {
                view.message(Const.ERROR, "model.already.exist");
                return;
            }
            
            view.redirect(null, "tbstructure");
            view.export("modelname", name);
            view.export("model", null);
            
            break;
        case Common.SH:
            model = documents.getModel("SEARCH_HELP");
            
            view.redirect(null, "shstructure");
            view.export("shname", name);
            view.export("shmodel", model);
            
            model = documents.getModel("SH_ITENS");
            view.export("shitens", model);
            
            break;
        }
        
        view.setReloadableView(true);
        view.export("mode", Common.CREATE);
    }
}
