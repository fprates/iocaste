package org.iocaste.sh;

import java.util.HashMap;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.View;

public class Request {

    public static final View choose(View vdata) {
        SearchHelp sh = vdata.getParameter("sh");
        View view = sh.getView();
        Parameter value = vdata.getElement("value");
        InputComponent input = view.getElement(sh.getInputName());
        
        if (input.isEnabled())
            input.set(value.get());
        
        return view;
    }
    
    public static void search(Context context) {
        ValueRange range;
        InputComponent input;
        DataForm form = context.view.getElement("criteria");
        
        context.criteria = new HashMap<>();
        
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
