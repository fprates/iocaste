package org.iocaste.datadict;

import java.util.Map;

import org.iocaste.datadict.Common.ItensNames;
import org.iocaste.documents.common.DataElement;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Add {

    public static final void main(ViewData view, Function function)
            throws Exception {
        byte mode = Common.getMode(view);
        Table itens = (Table)view.getElement("itens");
        Map<ItensNames, DataElement> references =
                Common.getFieldReferences(function);
        
        if (Common.hasItemDuplicated(view))
            return;
        
        Common.insertItem(itens, mode, null, references);
    }
}
