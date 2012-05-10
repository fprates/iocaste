package org.iocaste.shell;

import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.InputComponent;

public class InputStatus {
    public ControlComponent control = null;
    public int error = 0;
    public InputComponent input = null;
    public String fatal = null, message = null;
}