package org.iocaste.appbuilder.common.portal;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class PortalStyle extends AbstractViewConfigStyle {

	@Override
	public void execute() {
		load(".nc_login");
		put("display", "none");
	}
	
}