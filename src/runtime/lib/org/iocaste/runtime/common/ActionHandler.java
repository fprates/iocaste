package org.iocaste.runtime.common;

import org.iocaste.runtime.common.application.Context;

public interface ActionHandler {
	
    public abstract boolean isValidator();
    
	public abstract void run(Context context) throws Exception;

    public abstract void run(Context context,
            boolean redirectflag) throws Exception;

    public abstract void run(Context context,
            String page, boolean redirectflag) throws Exception;
}
