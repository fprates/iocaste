package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.StyleSheet;

public class ChangeSecretConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Button button;
        Context extcontext;
        MessageSource messages;
        DataForm form;
        InputComponent item;
        StyleSheet stylesheet;
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.get(".loginform").put("width", "19em");
        
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
        
        messages = new MessageSource();
        messages.setMessages(extcontext.messages);
        context.view.setMessages(messages);
        context.view.setTitle("password.change");
        
        button = getElement("changesecret");
        button.setSubmit(true);
    }

}
