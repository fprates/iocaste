package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class MainConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        InputComponent input;
        DataForm form;
        Context extcontext;
        
        extcontext = getExtendedContext();
        
        form = getElement("package");
        form.importModel(extcontext.globalcfgmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setObligatory(true);
        input.setVisible(true);
        
        context.view.setFocus(input);
        getNavControl().setTitle(Context.TITLES[Context.SELECT]);
    }

}
