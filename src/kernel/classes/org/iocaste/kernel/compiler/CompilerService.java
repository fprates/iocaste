package org.iocaste.kernel.compiler;

import org.iocaste.kernel.files.FileServices;
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;

public class CompilerService extends AbstractFunction {
    public Session session;
    public FileServices files;
    
    public CompilerService() {
        export("compile", new Compile());
    }
}
