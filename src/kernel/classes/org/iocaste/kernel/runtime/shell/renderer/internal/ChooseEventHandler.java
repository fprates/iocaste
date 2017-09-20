package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.AbstractEventHandler;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.PopupControl;

public class ChooseEventHandler extends AbstractEventHandler {
    private static final long serialVersionUID = -3350721390644999358L;
    private Map<String, Object> values;
    public PopupControl popupcontrol;
    
    public ChooseEventHandler(PopupControl popupcontrol) {
        values = new HashMap<>();
        this.popupcontrol = popupcontrol;
    }
    
    @Override
    public final void onEvent(byte event, ControlComponent control) {
        String action = control.getAction();
        popupcontrol.update(action, values.get(action));
    }
    
    public final void put(String key, Object value) {
        values.put(key, value);
    }
    
}
