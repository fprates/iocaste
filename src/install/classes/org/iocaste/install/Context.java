package org.iocaste.install;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.RadioGroup;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class Context extends AbstractExtendedContext {
    public View view;
    public ViewState state;
    public Function function;
    public RadioGroup group;
    
    public Context(PageBuilderContext context) {
        super(context);
        state = new ViewState();
    }
}
