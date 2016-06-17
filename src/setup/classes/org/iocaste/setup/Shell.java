package org.iocaste.setup;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DummyElement;

public class Shell extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement ticketid, appname, pagename, username, secret, locale;
        DataElement configname, configvalue;
        ModelInstall model;

        ticketid = elementchar("SHELL_TICKET_ID", 64, false);
        appname = elementchar("SHELL_APPNAME", 64, false);
        pagename = elementchar("SHELL_PAGENAME", 64, false);
        username = new DummyElement("LOGIN.USERNAME");
        secret = new DummyElement("LOGIN.SECRET");
        locale = elementchar("SHELL_LOCALE", 5, false);
        configname = elementchar("SHELL_CONFIG_NAME", 20, true);
        configvalue = elementchar("SHELL_CONFIG_VALUE", 64, false);
        
        /*
         * tickets
         */
        model = modelInstance("SHELL_TICKETS", "SHELL004");
        model.key("ID", "TKTID", ticketid);
        model.item("APP_NAME", "APPNM", appname);
        model.item("PAGE_NAME", "PAGEN", pagename);
        model.item("USERNAME", "USRNM", username);
        model.item("SECRET", "SECRT", secret);
        model.item("LOCALE", "LOCAL", locale);
        
        /*
         * shell config
         */
        model = modelInstance("SHELL_PROPERTIES", "SHELL006");
        model.key("NAME", "CFGNM", configname);
        model.item("VALUE", "CFGVL", configvalue);
        model.values("LOGIN_MANAGER", "iocaste-login");
        model.values("EXCEPTION_HANDLER", "iocaste-exhandler");
        
    }
}
