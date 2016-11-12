package org.iocaste.appbuilder.common.portal;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.ActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class PortalContext extends AbstractExtendedContext {
	public StandardPanel panel;
    public String email, secret;
    public ActionHandler load;

	public PortalContext(PageBuilderContext context) {
		super(context);
		panel = new StandardPanel(context);
	}

}
