package org.iocaste.kernel.runtime.shell.sh;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.shell.common.AbstractEventHandler;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class SearchEventHandler extends AbstractEventHandler {
    private static final long serialVersionUID = -5684585760530934805L;
    private Context context;
    
    public SearchEventHandler(Context context) {
        this.context = context;
    }
    
    @Override
    public void onEvent(byte event, ControlComponent control) {
        ValueRange range;
        InputComponent input;
        DataForm form = context.popup.viewctx.view.getElement("criteria");
        
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

