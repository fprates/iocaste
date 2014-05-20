package org.iocaste.shell.common;

import java.io.Serializable;

public interface ViewCustomAction extends Serializable {

    public abstract void execute(PageContext context);
}
