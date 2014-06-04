package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;

public class MaintainanceInput extends AbstractViewInput {
    private ExtendedContext extcontext;
    
    public MaintainanceInput(ExtendedContext extcontext) {
        this.extcontext = extcontext;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        DataForm form = getElement("base");
        
        setdfkey("head", extcontext.id);
        for (Element element : form.getElements()) {
            if (!element.isVisible())
                continue;
            context.view.setFocus(element);
            break;
        }
    }

}
