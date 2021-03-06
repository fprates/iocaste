package org.iocaste.appbuilder.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DummyElement;

public class PortalInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
    	ModelInstall model;
    	DataElement app, email, secret, username;
    	
    	app = elementchar("PORTAL_APP", 32, DataType.KEEPCASE);
    	email = elementchar("PORTAL_EMAIL", 64, DataType.KEEPCASE);
    	secret = elementchar("PORTAL_SECRET", 12, DataType.KEEPCASE);
    	username = new DummyElement("LOGIN.USERNAME");
    	
    	model = modelInstance("PORTAL_USERS", "PRTLUSRS");
    	model.namespace("APPNM", app);
        model.key("EMAIL", "EMAIL", email);
        model.item("USERNAME", "USRNM", username);
        
        model = modelInstance("PORTAL_USER_INPUT");
        model.key("EMAIL", email);
        model.item("SECRET", secret);
        
        context.getInstallData().addNumberFactory("PRTLUSRS");
    }

}
