package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        InputComponent input;
        DataForm form;
        
        getNavControl().setTitle("user-selection");
        
        form = getElement("selection");
        form.importModel(new Documents(context.function).getModel("LOGIN"));
        for (Element element : form.getElements()) {
            switch (element.getName()) {
            case "USERNAME":
                input = (InputComponent)element;
                context.view.setFocus(input);
                input.getModelItem().setSearchHelp("SH_USER");
                input.setObligatory(true);
                break;
            default:
                element.setVisible(false);
                break;
            }
        }
    }
}
