package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.StyleSheet;

public class SelectConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        StyleSheet stylesheet;
        RadioButton rbutton;
        DataForm dataform;
        NavControl navcontrol;
        
        navcontrol = getNavControl();
        navcontrol.add("create");
        navcontrol.add("show");
        navcontrol.add("update");
        navcontrol.add("delete");
        navcontrol.add("rename");
        
        dataform = getElement("model");
        dataform.importModel("DD_MODEL_ITEM", context.function);
        for (Element element : dataform.getElements())
            if (element.getName().equals("NAME")) {
                element.setVisible(true);
                ((DataItem)element).setObligatory(true);
                context.view.setFocus(element);
            } else{
                element.setVisible(false);
            }
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(".optlist");
        stylesheet.put(".optlist", "list-style-type", "none");
        
        getElement("optlist").setStyleClass("optlist");
        
        rbutton = getElement("tpobjtable");
        rbutton.setText("table");
        rbutton.setSelected(true);
        
        rbutton = getElement("tpobjsh");
        rbutton.setText("search.help");
    }

}
