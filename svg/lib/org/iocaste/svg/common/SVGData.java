package org.iocaste.svg.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SVGData implements Serializable {
    private static final long serialVersionUID = 3565051111567721139L;
    private List<SVGDataItem> items;
    
    public SVGData() {
        items = new ArrayList<>();
    }
    
    public final void circle(int cx, int cy, int r) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.CIRCLE;
        item.cx = cx;
        item.cy = cy;
        item.r = r;
        items.add(item);
    }
    
    public final List<SVGDataItem> get() {
        return items;
    }
    
    public final void line(int x1, int y1, int x2, int y2) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.LINE;
        item.x1 = x1;
        item.y1 = y1;
        item.x2 = x2;
        item.y2 = y2;
        items.add(item);
    }
    
    public final void rectangle(int x, int y, int w, int h) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.RECTANGLE;
        item.x1 = x;
        item.y1 = y;
        item.w = w;
        item.h = h;
        items.add(item);
    }
    
    public final void setDimension(int w, int h) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.DIMENSION;
        item.w = w;
        item.h = h;
        items.add(item);
    }
    
    public final void setStyle(String style) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.STYLE;
        item.style = style;
        items.add(item);
    }
}