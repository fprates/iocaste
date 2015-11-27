package org.iocaste.login;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.StandardPanelConfig;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.StyleSheet;

public class ChangeSecretConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Button button;
        Context extcontext;
        DataForm form;
        InputComponent item;
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
        
        extcontext = getExtendedContext();
        form = getElement("chgscrt");
        form.setStyleClass("loginform");
        form.importModel(extcontext.chgscrtmodel);
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (InputComponent)element;
            item.setSecret(true);
            item.setObligatory(true);
            switch (element.getName()) {
            case "SECRET":
                context.view.setFocus(item);
                break;
            }
        }
        
        context.view.setTitle("password.change");
        button = getElement("changesecret");
        button.setSubmit(true);
    }

}
