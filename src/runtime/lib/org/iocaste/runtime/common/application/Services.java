package org.iocaste.runtime.common.application;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.runtime.common.install.InstallApplication;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.messages.ApplicationMessageSource;
import org.iocaste.shell.common.messages.GetMessages;

public class Services<T extends Context> extends AbstractFunction
        implements ApplicationMessageSource {
    private Application<T> application;
    
    public Services(Application<T> application) {
        this.application = application;
        export("messages_get", new GetMessages());
        export("install", new InstallApplication());
    }

    @SuppressWarnings("unchecked")
    public final <C extends AbstractContext> C configOnly() throws Exception {
        T context = application.execute();
        AbstractContext compatctx = new CompatibilityContext();
        compatctx.messages = context.getMessageSource();
        return (C)compatctx;
    }
    
    public final Application<T> getApplication() {
        return application;
    }
}

class CompatibilityContext extends AbstractContext {
    
}
