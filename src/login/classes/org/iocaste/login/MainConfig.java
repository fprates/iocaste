package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Button button;
        DataFormToolData form;
        DataFormToolItem item;
        Context extcontext;
        
        context.view.setTitle("authentic");

        Style.set(context);
        getElement("logincnt").setStyleClass("logincnt");
        
        extcontext = getExtendedContext();
        form = getDataFormTool("login");
        form.style = "loginform";
        form.model = extcontext.loginmodel;
        
        item = form.itemInstance("USERNAME");
        item.focus = item.required = true;
        
        item = form.itemInstance("LOCALE");
        item.componenttype = Const.LIST_BOX;
        item.required = true;
        
        item = form.itemInstance("SECRET");
        item.secret = item.required = true;
        
        button = getElement("connect");
        button.setSubmit(true);
    }
}
