package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.internal.TrackingData;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class TextFieldRenderer extends Renderer {


    public static final XMLElement render(DataItem dataitem, String tbstyle,
            Config config) {
        return _render(dataitem, TextField.STYLE, tbstyle, config);
    }
    
    public static final XMLElement render(TextField textfield, String tbstyle,
            Config config) {
        return _render(textfield, textfield.getStyleClass(), tbstyle, config);
    }
    
    /**
     * 
     * @param textfield
     * @param config
     * @return
     */
    private static final XMLElement _render(InputComponent input,
            String style, String tablestyle, Config config) {
        Container container;
        PopupControl popupcontrol;
        StringBuilder sb;
        String tftext, calname, shname;
        Text text;
        SearchHelp search;
        Calendar calendar;
        XMLElement tagt, tagl, tagc;
        DataElement dataelement = Shell.getDataElement(input);
        int length = (dataelement == null)? input.getLength() :
            dataelement.getLength();
        String name = input.getHtmlName(), value = toString(input);
        XMLElement spantag, inputtag = new XMLElement("input");
        
        if (value == null)
            value = "";
        
        inputtag.add("type", (!input.isSecret())? "text" : "password");
        inputtag.add("name", name);
        inputtag.add("id", name);
        inputtag.add("size", Integer.toString(input.getVisibleLength()));
        inputtag.add("maxlength", Integer.toString(length));
        inputtag.add("value", value);
        inputtag.add("onfocus", new StringBuilder("send('").append(name).
                append("', '&event=onfocus', null)").toString());
        
        container = input.getContainer();
        if ((container != null) && (container.getType() == Const.TABLE_ITEM))
            sb = new StringBuilder("table_cell_content");
        else
            sb = new StringBuilder(style);
        
        if (!input.isEnabled()) {
            sb.append("_disabled");
            inputtag.add("readonly", "readonly");
        }
        
        if (dataelement != null)
            switch (dataelement.getType()) {
            case DataType.NUMC:
            case DataType.DEC:
                sb.append("_right");
                break;
            }
        
        inputtag.add("class", sb.toString());
        addEvents(inputtag, input);
        tagc = new XMLElement("td");
        tagc.addChild(inputtag);
        tagl = new XMLElement("tr");
        tagl.addChild(tagc);

        popupcontrol = config.getPopupControl();
        calendar = input.getCalendar();
        if ((calendar != null) && input.isEnabled()) {
            tagc = new XMLElement("td");
            if (popupcontrol != null) {
                calname = popupcontrol.getName();
                if ((calname.equals(calendar.getEarly()) ||
                     calname.equals(calendar.getLate()) ||
                     calname.equals(calendar.getName())))
                    tagc.addChildren(renderPopup(config));
            }
            
            tagc.addChild(CalendarButtonRenderer.render(calendar, config));
            tagl.addChild(tagc);
        }
        
        search = input.getSearchHelp();
        if (search != null) {
            tagc = new XMLElement("td");
            if (popupcontrol != null) {
                shname = popupcontrol.getHtmlName();
                if (shname.equals(search.getHtmlName()) ||
                    shname.equals(search.getChild()))
                   tagc.addChildren(renderPopup(config));
            }
            
            tagc.addChild(SHButtonRenderer.render(search, config));
            tagl.addChild(tagc);
        }
        
        if (input.isObligatory()) {
            spantag = new XMLElement("input");
            spantag.add("type", "button");
            spantag.add("class", "sh_button");
            spantag.add("value", "!");
            spantag.add("disabled", "disabled");
            tagc = new XMLElement("td");
            tagc.addChild(spantag);
            tagl.addChild(tagc);
        }
        
        tftext = input.getText();
        if (tftext != null) {
            text = new Text(config.getView(), "");
            text.setStyleClass("tftext");
            text.setText(tftext);
            tagc = new XMLElement("td");
            tagc.addChild(TextRenderer.render(text, config));
            tagl.addChild(tagc);
        }
        
        tagt = new XMLElement("table");
        tagt.addChild(tagl);
        if (tablestyle != null)
            tagt.add("style", tablestyle);
        
        return tagt;
    }
    
    private static final List<XMLElement> renderPopup(Config config) {
        Map<String, Object> parameters;
        Object[] viewreturn;
        PopupControl control;
        List<XMLElement> tags;
        TrackingData tracking;
        Service service;
        Message message;
        View view, sourceview;
        StyleSheet stylesheet;
        
        control = config.getPopupControl();
        tracking = config.getTracking();
        service = new Service(tracking.sessionid, tracking.contexturl);
        message = new Message("get_view_data");
        view = new View(control.getApplication(), "main");
        sourceview = config.getView();
        stylesheet = sourceview.styleSheetInstance();
        
        parameters = new HashMap<>();
        parameters.put("control", control);
        parameters.put("msgsource", sourceview.getAppName());
        parameters.put("action", config.getCurrentAction());
        parameters.put("form", config.getCurrentForm());
        view.setStyleSheet(stylesheet.getElements());
        
        message.add("view", view);
        message.add("init", true);
        message.add("parameters", parameters);
        viewreturn = (Object[])service.call(message);
        view = (View)viewreturn[0];
        
        control.update(view);
        config.getMessageSources().add(view.getMessages());
        config.getView().setStyleSheet(view.styleSheetInstance().getElements());
        
        tags = new ArrayList<>();
        for (Container container : view.getContainers())
            Renderer.renderContainer(tags, container, config);
        
        return tags;
    }
}
