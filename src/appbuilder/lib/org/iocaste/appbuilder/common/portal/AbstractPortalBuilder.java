package org.iocaste.appbuilder.common.portal;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Shell;

public abstract class AbstractPortalBuilder extends AbstractPageBuilder {
	private PortalContext portalctx;
	private String user, password, language;
	
	protected final void authentic(AbstractPanelPage page) {
		instance("authentic", page);
	}
	
	@Override
	public final void config(PageBuilderContext context) throws Exception {
        Iocaste iocaste;
		
		portalctx = contextInstance(context);
		config(portalctx);

        if (!portalctx.login)
        	return;
        iocaste = new Iocaste(context.function);
        if (!iocaste.isConnected()) {
            iocaste.login(user, password, language);
            user = password = language = null;
        }
        new Shell(context.function).pushPage(context.view);
	}

	protected abstract void config(PortalContext portalctx) throws Exception;
	
	protected PortalContext contextInstance(PageBuilderContext context) {
	    return new PortalContext(context);
	}
	
	protected final void instance(String name, AbstractPanelPage page) {
		portalctx.panel.instance(name, page, portalctx);
	}

	protected final void login(String user, String password, String language) {
		this.user = user;
		this.password = password;
		this.language = language;
	}
	
	protected final void newpassword(AbstractPanelPage page) {
	    instance("newpassword", page);
	}
	
	protected final void signup(AbstractPanelPage page) {
		instance("signup", page);
	}
}
