package org.iocaste.svg.common;

import java.io.Serializable;

public class SVGDataItem implements Serializable {
    private static final long serialVersionUID = -425079007035764899L;
    public SVGMethods type;
    public int w, h;
    public String style;
    public int cx, cy, r;
    public int x1, y1, x2, y2;
}
