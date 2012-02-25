package org.iocaste.datadict;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.ViewData;

public class Create {
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public static final void main(ViewData vdata, Function function)
            throws Exception {
        Documents documents = new Documents(function);
        String modelname = ((DataItem)vdata.getElement("modelname")).getValue();
        
        if (documents.hasModel(modelname)) {
            vdata.message(Const.ERROR, "model.already.exist");
            return;
        }
        
        vdata.setReloadableView(true);
        vdata.export("mode", Common.CREATE);
        vdata.export("modelname", modelname);
        vdata.export("model", null);
        vdata.redirect(null, "structure");
    }
}
