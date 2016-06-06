package org.iocaste.kernel.process;

import org.iocaste.protocol.AbstractFunction;

public class ProcessServices extends AbstractFunction {

    public ProcessServices() {
        export("external_process_execute", new ProcessExecute());
    }
}
