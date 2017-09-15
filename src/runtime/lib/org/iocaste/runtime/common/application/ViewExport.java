package org.iocaste.runtime.common.application;

import java.io.Serializable;
import java.util.Locale;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.ViewTitle;

public class ViewExport implements Serializable {
    private static final long serialVersionUID = 357578470548183403L;
    public Object[] items, msgargs, ncconfig;
    public HeaderLink[] links;
    public String[][] messages;
    public Object[][] stylesheet, styleconst, ncspec, reqparameters, subpages;
    public Locale locale;
    public String action, prefix, message, popupcontrol;
    public Const msgtype;
    public ViewTitle title;
}
