package org.iocaste.kernel.runtime.shell.sh;

import java.util.Map;

import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class Style {

    public static final void execute(Context context) {
        Map<String, String> style;
        StyleSheet stylesheet = StyleSheet.
                instance(context.popup.viewctx.view);
        Map<Integer, String> constants = stylesheet.getConstants();
        
        style = stylesheet.newElement(".shcnt");
        style.put("position", "absolute");
        style.put("background-color", constants.get(Shell.BACKGROUND_COLOR));
        style.put("float", "left");
        style.put("padding", "10px");
        style.put("border-style", "solid");
        style.put("border-color", constants.get(Shell.FRAME_COLOR));
        style.put("border-width", "1px");
        style.put("overflow", "hidden");
        style.put("z-index", "1");
        style.put("margin-top", "5px");
        style.put("margin-left", "5px");
        style.put("box-shadow", constants.get(Shell.SHADOW));
        
        style = stylesheet.newElement(".shdatacnt");
        style.put("overflow", "auto");
        style.put("height", "20em");
        
        style = stylesheet.newElement(".shcriteria");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("border-style", "none");
    }
}
