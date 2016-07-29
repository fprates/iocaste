package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.internal.Controller;
import org.iocaste.internal.EventHandler;
import org.iocaste.internal.TrackingData;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;
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
import org.iocaste.shell.common.View;

public class TextFieldRenderer extends AbstractElementRenderer<InputComponent> {
    
    public TextFieldRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.TEXT_FIELD);
    }

    private final boolean allowContextMenu(InputComponent input) {
        boolean required = input.isObligatory();
        boolean calendar = (input.isEnabled() && (input.getCalendar() != null));
        boolean tftext = (input.getText() != null);
        boolean search = (input.getSearchHelp() != null);
        
        return (required || tftext || search || calendar);
    }
    
    @Override
    protected final XMLElement execute(InputComponent input, Config config) {
        Container container;
        PopupControl popupcontrol;
        StringBuilder sb;
        String tftext, calname, shname, name, value, label, style, cellstyle;
        Text text;
        SearchHelp search;
        Calendar calendar;
        XMLElement tagt, inputtag, tag;
        DataElement dataelement;
        ContextMenu ctxmenu;
        EventHandler handler;
        boolean required;
        int length;
        
        if (!input.isVisible())
            return get(Const.PARAMETER).run(input, config);
        
        style = getStyle(input);
        label = (input.getType() == Const.DATA_ITEM)?
                ((DataItem)input).getLabel() : input.getName();
        dataelement = Shell.getDataElement(input);
        length = (dataelement == null)? input.getLength() :
            dataelement.getLength();
        name = input.getHtmlName();
        value = toString(input);
        if (value == null)
            value = "";

        inputtag = new XMLElement("input");
        inputtag.add("type", (!input.isSecret())? "text" : "password");
        inputtag.add("name", name);
        inputtag.add("id", name);
        inputtag.add("maxlength", Integer.toString(length));
        inputtag.add("value", value);
        
        handler = config.actionInstance(name);
        handler.name = name;
        handler.event = "focus";
        handler.call = new StringBuilder("_send('").append(name).
                append("', '&event=onfocus', null);").toString();
        
        container = input.getContainer();
        if ((container != null) && (container.getType() == Const.TABLE_ITEM)) {
            sb = new StringBuilder("table_cell_content");
            cellstyle = "float:right;margin:0px;padding:0px;"
                    + "list-style-type: none";
        } else {
            sb = new StringBuilder(style);
            cellstyle = "margin:0px;padding:0px;list-style-type:none";
        }
        
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
        
        addAttributes(inputtag, input);

        tag = new XMLElement("li");
        tag.addChild(inputtag);
        tagt = new XMLElement("ul");
        tagt.add("style", cellstyle);
        tagt.addChild(tag);

        if (input.hasPlaceHolder()) {
            tag.add("style", "display:inline;float:left;width:100%;");
            inputtag.add("placeholder", label);
            sb.append("_internallabel");
            inputtag.add("class", sb.toString());
            return tagt;
        }
        
        if (input.hasNoHelper()) {
            inputtag.add("class", sb.toString());
            return tagt;
        }

        tag.add("style", "display:inline;float:left");
        inputtag.add("size", Integer.toString(input.getVisibleLength()));
        inputtag.add("class", sb.toString());
        
        if (!allowContextMenu(input))
            return tagt;
        
        ctxmenu = new ContextMenu(tagt, name);
        ctxmenu.setMessages(Controller.messages);
        required = input.isObligatory();
        if (required)
            ctxmenu.add(Controller.messages.get("required"));

        popupcontrol = config.getPopupControl();
        calendar = input.getCalendar();
        if (calendar != null) {
            ctxmenu.add(calendar.getHtmlName(), config, "calendar");
            if (popupcontrol != null) {
                calname = popupcontrol.getName();
                if ((calname.equals(calendar.getEarly()) ||
                     calname.equals(calendar.getLate()) ||
                     calname.equals(calendar.getName()))) {
                    tag = new XMLElement("li");
                    tag.addChildren(renderPopup(config));
                    tagt.addChild(tag);
                }
            }
        }

        search = input.getSearchHelp();
        if (search != null) {
            ctxmenu.add(search.getHtmlName(), config, "values");
            if (popupcontrol != null) {
                shname = popupcontrol.getHtmlName();
                if (shname.equals(search.getHtmlName()) ||
                    shname.equals(search.getChild())) {
                    tag = new XMLElement("li");
                    tag.addChildren(renderPopup(config));
                    tagt.addChild(tag);
                }
            }
        }

        tftext = input.getText();
        if (tftext != null) {
            text = new Text(config.getView(), "");
            text.setTag("li");
            text.setStyleClass("tftext");
            text.setText(tftext);
            tag = get(Const.TEXT).run(text, config);
            tagt.addChild(tag);
        }
        
        return tagt;
    }
    
    private final List<XMLElement> renderPopup(Config config) {
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
        service = new StandardService(tracking.sessionid, tracking.contexturl);
        message = new Message("get_view_data");

        sourceview = config.getView();
        stylesheet = sourceview.styleSheetInstance();
        view = new View(control.getApplication(), "main");
        view.importStyle(stylesheet);
        
        parameters = new HashMap<>();
        parameters.put("control", control);
        parameters.put("msgsource", sourceview.getAppName());
        parameters.put("action", config.getCurrentAction());
        parameters.put("form", config.getCurrentForm());
        
        message.add("view", view);
        message.add("init", true);
        message.add("parameters", parameters);
        viewreturn = (Object[])service.call(message);
        view = (View)viewreturn[0];
        
        control.update(view);
        config.getView().importStyle(view.styleSheetInstance());
        
        tags = new ArrayList<>();
        for (Container container : view.getContainers())
            get(container.getType()).run(tags, container, config);
        
        return tags;
    }
}
