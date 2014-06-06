package org.iocaste.shell.common;

import java.io.Serializable;

public interface ViewCustomAction extends Serializable {

    public abstract void execute(AbstractContext context) throws Exception;
}
