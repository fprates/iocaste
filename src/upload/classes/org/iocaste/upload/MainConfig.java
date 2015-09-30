package org.iocaste.upload;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;

public class MainConfig extends AbstractViewConfig {

    @Override
    public final void execute(PageBuilderContext context) {
        DataForm dataform;
        Form form;
        InputComponent input;
        
        form = getElement("main");
        form.setEnctype("multipart/form-data");
        
        dataform = getElement("options");
        dataform.importModel("UPL_OPTIONS", context.function);
        input = dataform.get("FILE");
        input.setObligatory(true);
        input.setVisibleLength(64);
        input.setComponentType(Const.FILE_ENTRY);
        
        dataform.get("LAYOUT").setObligatory(true);
        dataform.get("TRUNCATE_CHAR").setComponentType(Const.CHECKBOX);
    }
}
