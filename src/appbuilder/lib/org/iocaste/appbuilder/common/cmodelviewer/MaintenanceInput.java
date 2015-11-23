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

        cmodel = extcontext.document.getModel();
        if (cmodel.getHeader().getNamespace() != null) {
            dfnsset("head", extcontext.ns);
            dfnsset("base", extcontext.ns);
        }
        
        dfset("base", extcontext.document.getHeader());
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
