package org.iocaste.login;

import java.util.Map;

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
        Map<String, String> style;
        
        context.view.setTitle("authentic");

        Style.set(context);
        style = context.view.styleSheetInstance().get(".nc_title");
        style.put("text-align", "center");
        style.put("margin-left", "auto");
        style.put("margin-right", "auto");
        style.put("width", "600px");
        
        getElement("logincnt").setStyleClass("logincnt");
        
        extcontext = getExtendedContext();
        form = getTool("login");
        form.style = "loginform";
        form.model = extcontext.loginmodel;
        form.internallabel = true;
        
        item = form.itemInstance("USERNAME");
        item.focus = item.required = true;
        
        item = form.itemInstance("LOCALE");
        item.componenttype = Const.LIST_BOX;
        item.required = true;
        
        item = form.itemInstance("SECRET");
        item.secret = item.required = true;
        
        button = getElement("connect");
        button.setSubmit(true);
        button.setStyleClass("loginsubmit");
    }
}
