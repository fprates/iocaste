package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class SelectionConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        InputComponent input;
        NavControl navcontrol;
        DataForm dataform;
        
        navcontrol = getNavControl();
        navcontrol.add("display");
        navcontrol.add("edit");
        navcontrol.setTitle("dataeditor-selection");
        
        dataform = getElement("model");
        dataform.importModel("MODEL", context.function);
        for (Element element : dataform.getElements())
            element.setVisible(false);
        
        input = dataform.get("NAME");
        input.setObligatory(true);
        input.setVisible(true);
        context.view.setFocus(input);

    }

}
