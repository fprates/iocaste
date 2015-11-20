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
        
        dfnsset("head", extcontext.ns);
        dfkeyset("head", extcontext.id);
        for (Element element : form.getElements()) {
            if (!element.isDataStorable() || !element.isVisible() ||
                    !element.isEnabled())
                continue;
            context.view.setFocus(element);
            break;
        }
        
        if (extcontext.document == null)
            return;

        dfnsset("base", extcontext.ns);
        dfset("base", extcontext.document.getHeader());
        cmodel = extcontext.document.getModel();
        for (String name : cmodel.getItems().keySet())
            tableitemsset(name.concat("_table"), extcontext.document.
                    getItems(name));
        
        loadInputTexts(context);
    }
    
    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
}
