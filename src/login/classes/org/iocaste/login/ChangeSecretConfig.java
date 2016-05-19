package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.shell.common.Button;

public class ChangeSecretConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Button button;
        Context extcontext;
        DataFormToolData form;
        DataFormToolItem item;

        Style.set(context);
        getElement("chgscrtcnt").setStyleClass("logincnt");

        getNavControl().setTitle("password.change");
        
        extcontext = getExtendedContext();
        form = getTool("chgscrt");
        form.style = "loginform";
        form.model = extcontext.chgscrtmodel;
        item = form.itemInstance("SECRET");
        item.secret = item.required = item.focus = true;
        
        item = form.itemInstance("CONFIRM");
        item.secret = item.required = true;
        
        button = getElement("changesecret");
        button.setSubmit(true);
    }

}
