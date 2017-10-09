package org.iocaste.kernel.runtime.shell;

import org.iocaste.kernel.runtime.shell.renderer.internal.AbstractProcessHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.ControllerData;
import org.iocaste.kernel.runtime.shell.renderer.internal.InputStatus;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.tooldata.ViewExport;

public class ProcessLegacyInput extends AbstractProcessHandler {

    @Override
    public Object run(Message message) throws Exception {
        InputStatus status;
        ProcessInput input = getFunction().get("input_process");
        Object[] values = new Object[7];
        ControllerData config = new ControllerData();
        
        config.state.viewctx = new ViewContext(message.get("view"));
        config.state.viewctx.viewexport = new ViewExport();
        config.values.putAll(message.get("values"));
        
        status = input.run(config);
        values[0] = config.state.viewctx.view;
        values[1] = status.fatal;
        values[2] = config.state.viewctx.viewexport.msgtype;
        values[3] = config.state.viewctx.viewexport.msgargs;
        values[4] = config.state.viewctx.viewexport.message;
        values[5] = status.event;
        values[6] = status.control;
        
        return values;
    }

}
