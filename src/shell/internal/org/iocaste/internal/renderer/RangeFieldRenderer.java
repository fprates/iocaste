package org.iocaste.internal.renderer;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.internal.Input;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.RangeFieldPair;
import org.iocaste.shell.common.RangeInputComponent;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class RangeFieldRenderer extends Renderer {

    private static final void copyCalendar(
            View view, Calendar to, Calendar from) {
        switch (from.getMode()) {
        case Calendar.EARLY:
            ((Calendar)view.getElement(to.getEarly())).setDate(from.getDate());
            break;
        case Calendar.LATE:
            ((Calendar)view.getElement(to.getLate())).setDate(from.getDate());
            break;
        default:
            to.setDate(from.getDate());
            break;
        }
    }
    
    public static final XMLElement render(
            RangeInputComponent rangeinput, Config config) {
        View view;
        PopupControl popupcontrol;
        String style, low, high;
        DataElement dataelement;
        ValueRange range;
        ValueRangeItem value;
        TextField tfield;
        RangeFieldPair[] elements;
        XMLElement to, tabletag, trtag, tdtag;
        InputComponent input;
        Calendar calendar, fromcal;
        int length;
        
        low = rangeinput.getLowHtmlName();
        high = rangeinput.getHighHtmlName();
        view = config.getView();
        
        elements = new RangeFieldPair[3];
        elements[0] = new RangeFieldPair(view, low);
        elements[0].setMaster(rangeinput);
        elements[1] = null;
        elements[2] = new RangeFieldPair(view, high);
        elements[2].setMaster(rangeinput);
        
        input = (InputComponent)rangeinput;
        range = (ValueRange)input.get();
        value = (range == null)? null : range.get(0);
        
        tabletag = new XMLElement("table");
        trtag = new XMLElement("tr");
        tabletag.addChild(trtag);

        calendar = input.getCalendar();
        for (Element element : elements) {
            tdtag = new XMLElement("td");
            trtag.addChild(tdtag);
            
            if (element == null) {
                to = new XMLElement("p");
                to.addInner("até");
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

            if (calendar != null) {
                Input.generateCalendar(
                        view, rangeinput.getContainer(), tfield);
                
                for (Calendar childcal : new Calendar[] {
                        (Calendar)view.getElement(calendar.getName()),
                        (Calendar)view.getElement(calendar.getEarly()),
                        (Calendar)view.getElement(calendar.getLate())
                })
                    copyCalendar(view, tfield.getCalendar(), childcal);
            }
            
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
            popupcontrol = config.getPopupControl();
            if ((calendar == null) || (popupcontrol == null))
                continue;
            fromcal = (Calendar)popupcontrol;
            copyCalendar(view, calendar, fromcal);
            if (fromcal.getMaster() != null)
                calendar.setDate(tfield.getCalendar().getDate());
        }
        
        return tabletag;
    }
}
