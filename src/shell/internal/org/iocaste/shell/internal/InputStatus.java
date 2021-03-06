package org.iocaste.shell.internal;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.InputComponent;

public class InputStatus {
    public ControlComponent control;
    public int error = 0;
    public Const msgtype;
    public Object[] msgargs;
    public InputComponent input = null;
    public String fatal, message;
    public boolean event;
}