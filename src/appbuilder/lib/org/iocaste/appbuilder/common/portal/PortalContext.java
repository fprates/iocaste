package org.iocaste.appbuilder.common.portal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.ActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.CModelViewerContext;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.appbuilder.common.portal.tiles.PortalPageTiles;

public class PortalContext extends CModelViewerContext {
	public StandardPanel panel;
    public String email, secret, userprofile, username;
    public ActionHandler load;
    public Map<String, PortalPageTiles> pagetiles;

	public PortalContext(PageBuilderContext context) {
		super(context);
		panel = new StandardPanel(context);
		pagetiles = new HashMap<>();
	}

}
