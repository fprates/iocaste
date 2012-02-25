package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.ViewData;

public class Delete {
    public static final void main(ViewData view, Function function)
            throws Exception {
        Documents documents = new Documents(function);
        String modelname = ((DataItem)view.getElement("modelname")).getValue();
        
        if (!documents.hasModel(modelname)) {
            view.message(Const.ERROR, "model.not.found");
            return;
        }
        
        documents.removeModel(modelname);
        documents.commit();
        
        view.message(Const.STATUS, "model.removed.sucessfully");
    }

}
