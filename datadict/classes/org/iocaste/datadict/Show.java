package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.ViewData;

public class Show {

    public static final void main(ViewData view, Function function)
            throws Exception {
        DocumentModel model;
        String modelname = ((DataItem)view.getElement("modelname")).getValue();
        Documents documents = new Documents(function);
        
        if (!documents.hasModel(modelname)) {
            view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        model = documents.getModel(modelname);
        
        view.setReloadableView(true);
        view.export("mode", Common.SHOW);
        view.export("model", model);
        view.redirect(null, "structure");
    }
}
