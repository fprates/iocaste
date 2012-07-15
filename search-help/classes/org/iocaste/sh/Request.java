package org.iocaste.sh;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.protocol.Function;
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
    
    public static final void search(View view, Function function) {
        ValueRange range;
        InputComponent input;
        Map<String, ValueRange> values = new HashMap<String, ValueRange>();
        DataForm form = view.getElement("criteria");
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            input = (DataItem)element;
            range = input.get();
            if (range == null)
                continue;
            values.put(input.getModelItem().getName(), range);
        }
        
        view.setReloadableView(true);
        view.export("criteria", values);
    }
}
