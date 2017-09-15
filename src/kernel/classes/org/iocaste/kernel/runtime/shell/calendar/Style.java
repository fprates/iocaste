package org.iocaste.kernel.runtime.shell.calendar;

import java.util.Map;

import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class Style {
    
    public static final void execute(CalendarData data) {
        StyleSheet stylesheet;
        Map<String, String> style;
        Map<Integer, String> constants;
        
        stylesheet = StyleSheet.instance(data.popup.viewctx.view);
        constants = stylesheet.getConstants();
        style = stylesheet.newElement(".calcnt");
        style.put("position", "absolute");
        style.put("padding", "10px");
        style.put("float", "left");
        style.put("overflow", "hidden");
        style.put("background-color", constants.get(Shell.BACKGROUND_COLOR));
        style.put("border-style", "solid");
        style.put("border-width", "1px");
        style.put("border-color", constants.get(Shell.FRAME_COLOR));
        style.put("z-index", "1");
        style.put("margin-top", "2em");
        style.put("box-shadow", constants.get(Shell.SHADOW));
        
        style = stylesheet.clone(".calkey", ".link");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("text-align", "middle");
        style.put("font-weight", "normal");
        style.put("display", "block");
        style = stylesheet.clone(".calkey:hover", ".calkey");
        
        style = stylesheet.clone(".caltoday", ".link");
        style.put("padding", "0px");
        style.put("margin", "0px");
        style.put("text-align", "middle");
        style.put("font-weight", "bold");
        style.put("display", "block");
        style = stylesheet.clone(".caltoday:hover", ".caltoday");
        
        style = stylesheet.clone(".caldate", ".text");
        style.put("display", "inline");
        style.put("text-decoration", "none");

        style = stylesheet.clone(".calmonth", ".caldate");
        style.put("color", constants.get(Shell.CLICKABLE_COLOR));
        style.put("cursor", "pointer");

        style = stylesheet.clone(".calthead", ".table_head");
        style.put("display", "table-header-group");
        
        style = stylesheet.clone(".caltd", ".table_cell");
        style.put("display", "table-cell");
        style.put("float", "unset");
        style.remove("width");
    }
}
