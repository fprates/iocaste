package org.iocaste.internal.renderer;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.RangeFieldPair;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.TextField;

public class RangeFieldRenderer extends Renderer {

    public static final XMLElement render(
            RangeInputComponent rangeinput, Config config) {
        String style, low, high;
        DataElement dataelement;
        ValueRange range;
        ValueRangeItem value;
        TextField tfield;
        RangeFieldPair[] elements;
        XMLElement to, tabletag, trtag, tdtag;
        InputComponent input;
        int length;
        
        low = rangeinput.getLowHtmlName();
        high = rangeinput.getHighHtmlName();
        
        elements = new RangeFieldPair[3];
        elements[0] = new RangeFieldPair(config.getView(), low);
        elements[0].setMaster(rangeinput);
        elements[1] = null;
        elements[2] = new RangeFieldPair(config.getView(), high);
        elements[2].setMaster(rangeinput);
        
        input = (InputComponent)rangeinput;
        range = (ValueRange)input.get();
        value = (range == null)? null : range.get(0);
        
        tabletag = new XMLElement("table");
        trtag = new XMLElement("tr");
        tabletag.addChild(trtag);
        
        for (Element element : elements) {
            tdtag = new XMLElement("td");
            trtag.addChild(tdtag);
            
            if (element == null) {
                to = new XMLElement("p");
                to.addInner(config.getText("to", "to"));
                to.add("class", "text");
                tdtag.addChild(to);
                continue;
            }
            
            dataelement = input.getDataElement();
            tfield = (TextField)element;
            tfield.setObligatory(input.isObligatory());
            tfield.setSecret(input.isSecret());
            tfield.setModelItem(input.getModelItem());
            tfield.setEnabled(input.isEnabled());
            tfield.setDataElement(dataelement);
            tfield.setSearchHelp(input.getSearchHelp());
            tfield.setLocale(input.getLocale());
            
            if (dataelement != null) {
                length = dataelement.getLength();
                tfield.setVisibleLength((length > 20)? 20 : length);
            }
            
            if (element.getName().equals(low))
                tfield.set((value == null)? null : value.getLow());
            else
                tfield.set((value == null)? null : value.getHigh());
            
            style = "display: inline;";
            tdtag.addChild(TextFieldRenderer.render(tfield, style, config));
        }
        
        return tabletag;
    }
}
