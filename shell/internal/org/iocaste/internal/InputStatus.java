package org.iocaste.internal;

import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.InputComponent;

public class InputStatus {
    public ControlComponent control = null;
    public byte error = 0;
    public InputComponent input = null;
    public String fatal = null, message = null;
    public boolean event = false;
}