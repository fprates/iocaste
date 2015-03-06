package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;

public class MaintenanceInput extends AbstractViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ComplexModel cmodel;
        DataForm form = getElement("base");
        Context extcontext = getExtendedContext();
        
        setdfkey("head", extcontext.id);
        for (Element element : form.getElements()) {
            if (!element.isDataStorable() || !element.isVisible() ||
                    !element.isEnabled())
                continue;
            context.view.setFocus(element);
            break;
        }
        
        if (extcontext.document == null)
            return;
        
        setdf("base", extcontext.document.getHeader());
        cmodel = extcontext.document.getModel();
        for (String name : cmodel.getItems().keySet())
            tableitemsadd(name.concat("_table"), extcontext.document.
                    getItems(name));
        
        loadInputTexts(context);
    }
    
    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
}
