package org.iocaste.sh;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    /**
     * 
     * @param controldata
     * @param view
     */
    @Override
    public void back() {
        String position;
        PageStackItem entry;
        
        entry = new Shell(this).popPage();
        position = new StringBuilder(entry.getApp()).append(".").
                append(entry.getPage()).toString();
        backTo(position);
        setReloadableView(false);
    }
    
    @Override
    public final AbstractContext init(View view) {
        Messages messages;
        
        context = new Context();
        messages = new Messages();
        messages.setContext(context);
        messages.entries();
        
        return context;
    }
    
    /**
     * 
     */
    public void main() {
        ValueRange range;
        InputComponent input;
        DataForm criteria;
        
        context.control = context.function.getParameter("control");
        if (context.control.getMaster() != null) {
            criteria = context.control.getView().getElement("criteria");
            for (Element element : criteria.getElements()) {
                input = (InputComponent)element;
                range = input.get();
                if (range == null)
                    continue;
                context.criteria.put(element.getName(), range);
            }
        }
        
        Response.main(context);
        setMessageSource(context.model.getPackage());
    }
}
