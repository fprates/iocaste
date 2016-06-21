package org.iocaste.kernel.process;

import org.iocaste.kernel.files.FileServices;
import org.iocaste.protocol.AbstractFunction;

public class ProcessServices extends AbstractFunction {
    public FileServices files;
    
    public ProcessServices() {
        export("external_process_execute", new ProcessExecute());
        export("meta_context_get", new MetaContextGet());
    }
}
