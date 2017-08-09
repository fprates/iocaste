package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.navcontrol.StandardNavControlPage;

public class StandardPage extends AbstractPage {

	public StandardPage(AbstractPage child) {
		put("navcontrol", new StandardNavControlPage());
		put("custom", child);
	}
	
	@Override
	protected void execute() throws Exception {
		set(new StandardPanelSpec());
		set(new StandardPageConfig());
		set(new StandardPageInput());
	}

}
