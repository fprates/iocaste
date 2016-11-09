package org.iocaste.upload.main;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;

public class MainConfig extends AbstractViewConfig {

    @Override
    public final void execute(PageBuilderContext context) {
        DataFormToolData dataform;
        DataFormToolItem input;
        Form form;
        
        form = getElement("main");
        form.setEnctype("multipart/form-data");
        
        dataform = getTool("options");
        dataform.model = "UPL_OPTIONS";
        input = dataform.instance("FILE");
        input.required = true;
        input.vlength = 64;
        input.componenttype = Const.FILE_ENTRY;
        
        dataform.instance("LAYOUT").required = true;
        dataform.instance("TRUNCATE_CHAR").componenttype = Const.CHECKBOX;
    }
}
