package org.iocaste.login;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.StyleSheet;

public class ChangeSecretConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Button button;
        Context extcontext;
        DataFormToolData form;
        DataFormToolItem item;
        StyleSheet stylesheet;
        Map<String, String> style;
        
        stylesheet = context.view.styleSheetInstance();
        style = stylesheet.get(".outer_content");
        style.put("left", "0px");
        style.put("width", "100%");
        style.put("height", new StringBuilder("calc(100% - ").
                append(StandardPanelConfig.CONTENT_TOP).
                append(")").toString());
        
        stylesheet.get(".loginform").put("width", "19em");
        stylesheet.get(".std_panel_context").put("display", "none");
        
        getElement("chgscrtcnt").setStyleClass("logincnt");

        getNavControl().setTitle("password.change");
        
        extcontext = getExtendedContext();
        form = getDataFormTool("chgscrt");
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
