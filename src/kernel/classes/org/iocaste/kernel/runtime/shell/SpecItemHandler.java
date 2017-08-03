package org.iocaste.kernel.runtime.shell;

import org.iocaste.kernel.runtime.RuntimeEngine;

public interface SpecItemHandler {

    public void execute(RuntimeEngine shell, String name);
}
