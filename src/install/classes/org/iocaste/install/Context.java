package org.iocaste.install;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.RadioGroup;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Context {
    public View view;
    public ViewState state;
    public Function function;
    public RadioGroup group;
    
    public Context() {
        state = new ViewState();
    }
}
