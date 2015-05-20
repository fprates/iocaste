package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;

public class NSInputConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        InputComponent input;
        DataForm form;
        
        extcontext = getExtendedContext();
        form = getElement("ns");
        
        input = new DataItem(form, Const.TEXT_FIELD, "NSKEY");
        input.setDataElement(extcontext.nsitem.getDataElement());
        input.setObligatory(true);
        
        getNavControl().setTitle("ns.key.input");
    }

}
