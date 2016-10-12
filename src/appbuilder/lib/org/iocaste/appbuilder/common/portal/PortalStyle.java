package org.iocaste.appbuilder.common.portal;

import org.iocaste.appbuilder.common.style.AbstractViewConfigStyle;

public class PortalStyle extends AbstractViewConfigStyle {

	@Override
	public void execute() {
		instance(".portal_viewport");
		put("list-style-type", "none");
		put("margin", "0px");
		put("padding", "2px");
		
		clone(".portal_button", ".button");
		put("width", "100%");
		
		clone(".portal_login_option", ".link");
		put("width", "100%");
		put("text-align", "center");
		put("display", "block");
		
		get(".nc_login");
		put("display", "none");
	}
	
}