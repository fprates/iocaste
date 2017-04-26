package org.iocaste.runtime.common;

import org.iocaste.runtime.common.application.Context;

public interface ActionHandler<C extends Context> {
	
	public abstract void run(C context) throws Exception;

    public abstract void run(C context, boolean redirectflag) throws Exception;

    public abstract void run(C context, String page, boolean redirectflag)
    		throws Exception;
}
