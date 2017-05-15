package org.iocaste.runtime.common.application;

import org.iocaste.protocol.Function;
import org.iocaste.runtime.common.install.InstallContext;

public interface Application<T extends Context> extends Function {
    
    public abstract T execute();
	
	public abstract String getAppName();
	
	public abstract void install(InstallContext installctx);
}

