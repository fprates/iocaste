package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;

public class SelectConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataForm dataform;
        NavControl navcontrol;
        
        navcontrol = getNavControl();
        navcontrol.add("show");
        
        dataform = getElement("model");
        dataform.importModel("MODEL", context.function);
        for (Element element : dataform.getElements())
            if (element.getName().equals("NAME")) {
                element.setVisible(true);
                ((DataItem)element).setObligatory(true);
                context.view.setFocus(element);
            } else{
                element.setVisible(false);
            }
    }
}
