package org.iocaste.svg.common;

import java.io.Serializable;

import org.iocaste.shell.common.Form;

public class SVGDataItem implements Serializable {
    private static final long serialVersionUID = -425079007035764899L;
    public SVGMethods type;
    public int w, h, r;
    public String style;
    public int x1, y1, x2, y2;
    public String text;
    public SVGDataItem dataitem;
    public String action;
    public Form form;
}
