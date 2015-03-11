package org.iocaste.sh;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class Request {
    
    public static void search(Context context) {
        ValueRange range;
        InputComponent input;
        DataForm form = context.control.getView().getElement("criteria");
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            input = (DataItem)element;
            range = input.get();
            if (range == null)
                continue;
            context.criteria.put(input.getModelItem().getName(), range);
        }
    }
}
