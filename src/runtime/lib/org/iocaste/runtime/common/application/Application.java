package org.iocaste.runtime.common.application;

import org.iocaste.runtime.common.Function;

public interface Application extends Function {
	
	public abstract Context config();
	
	public abstract String getAppName();
}

