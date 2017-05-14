package org.iocaste.runtime.common.application;

import org.iocaste.protocol.Function;

public interface Application<T extends Context> extends Function {
    
    public abstract T execute();
	
	public abstract String getAppName();
}

