package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        InputComponent input;
        Context extcontext;
        DataForm form;
        
        extcontext = getExtendedContext();
        
        form = getElement("model");
        form.importModel(extcontext.modelmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setVisible(true);
        input.setObligatory(true);

        context.view.setFocus(input);
        getNavControl().setTitle("dataview-selection");
    }

}
