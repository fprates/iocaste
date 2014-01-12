package org.iocaste.svg.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Parameter;

public class SVGData implements Serializable {
    private static final long serialVersionUID = 3565051111567721139L;
    private List<SVGDataItem> items;
    
    public SVGData() {
        items = new ArrayList<>();
    }
    
    public final void axlink(SVGDataItem dataitem, Form form, String action,
            Map<Parameter, Object> parameters) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.AXLINK;
        item.dataitem = dataitem;
        item.action = action;
        item.form = form;
        item.linkvalues = parameters;
        items.add(item);
    }
    
    public final void circle(int cx, int cy, int r) {
        items.add(scircle(cx, cy, r));
    }
    
    public final List<SVGDataItem> get() {
        return items;
    }
    
    public final void line(int x1, int y1, int x2, int y2) {
        items.add(sline(x1, y1, x2, y2));
    }
    
    public final void rectangle(int x, int y, int w, int h) {
        items.add(srectangle(x, y, w, h));
    }
    
    public static final SVGDataItem scircle(int cx, int cy, int r) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.CIRCLE;
        item.x1 = cx;
        item.y1 = cy;
        item.r = r;
        return item;
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

    public static final SVGDataItem sline(int x1, int y1, int x2, int y2) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.LINE;
        item.x1 = x1;
        item.y1 = y1;
        item.x2 = x2;
        item.y2 = y2;
        return item;
    }

    public static final SVGDataItem srectangle(int x, int y, int w, int h) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.RECTANGLE;
        item.x1 = x;
        item.y1 = y;
        item.w = w;
        item.h = h;
        return item;
    }
    
    public final SVGDataItem text(int x, int y, String text) {
        SVGDataItem item = new SVGDataItem();
        
        item.type = SVGMethods.TEXT;
        item.x1 = x;
        item.y1 = y;
        item.text = text;
        items.add(item);
        return item;
    }
}