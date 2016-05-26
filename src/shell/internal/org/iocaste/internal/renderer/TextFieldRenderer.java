package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.internal.Controller;
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

    private static final boolean allowContextMenu(InputComponent input) {
        boolean required = input.isObligatory();
        boolean calendar = (input.isEnabled() && (input.getCalendar() != null));
        boolean tftext = (input.getText() != null);
        boolean search = (input.getSearchHelp() != null);
        
        return (required || tftext || search || calendar);
        
    }
    
    public static final XMLElement render(DataItem dataitem, String tbstyle,
            Config config) {
        return _render(dataitem,
                TextField.STYLE, tbstyle, config, dataitem.getLabel());
    }
    
    public static final XMLElement render(TextField textfield, String tbstyle,
            Config config) {
        return _render(textfield, textfield.getStyleClass(),
                tbstyle, config, textfield.getName());
    }
    
    /**
     * 
     * @param textfield
     * @param config
     * @return
     */
    private static final XMLElement _render(InputComponent input,
            String style, String cellstyle, Config config, String label) {
        Container container;
        PopupControl popupcontrol;
        StringBuilder sb;
        String tftext, calname, shname, name, value, tfcontext;
        Text text;
        SearchHelp search;
        Calendar calendar;
        XMLElement tagt, inputtag, tag, options;
        boolean required;
        DataElement dataelement;
        int length;
        
        if (!input.isVisible())
            return ParameterRenderer.render(input);
        
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
        inputtag.add("onfocus", new StringBuilder("_send('").append(name).
                append("', '&event=onfocus', null)").toString());
        
        container = input.getContainer();
        if ((container != null) && (container.getType() == Const.TABLE_ITEM)) {
            sb = new StringBuilder("table_cell_content");
            cellstyle = "float:right;margin:0px;padding:0px;"
                    + "list-style-type: none";
        } else {
            sb = new StringBuilder(style);
            cellstyle = "margin:0px;padding:0px;list-style-type: none";
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
        
        addEvents(inputtag, input);

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

        tag.add("style", "display:inline;float:left;");
        inputtag.add("size", Integer.toString(input.getVisibleLength()));
        inputtag.add("class", sb.toString());
        
        if (!allowContextMenu(input))
            return tagt;
        
        tfcontext = name.concat("_menu");
        tag = new XMLElement("li");
        renderOpenMenuButton(tag, tfcontext, name);
        tagt.addChild(tag);
        tag = new XMLElement("li");
        renderCloseMenuButton(tag, tfcontext, name);
        tagt.addChild(tag);
        tag = new XMLElement("li");
        options = new XMLElement("ul");
        options.add("id", tfcontext);
        options.add("style", "display:none");
        options.add("class", "ctxmenu");
        tag.addChild(options);
        tagt.addChild(tag);


        required = input.isObligatory();
        if (required) {
            tag = new XMLElement("li");
            tag.add("class", "ctxmenu_item");
            tag.addInner(Controller.messages.get("required"));
            options.addChild(tag);
        }

        popupcontrol = config.getPopupControl();
        calendar = input.getCalendar();
        if (calendar != null) {
            tag = new XMLElement("li");
            tag.add("class", "ctxmenu_item");
            tag.addChild(ContextMenuButtonRenderer.render(
                    calendar.getHtmlName(), config, "calendar"));
            options.addChild(tag);
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
            tag = new XMLElement("li");
            tag.add("class", "ctxmenu_item");
            tag.addChild(ContextMenuButtonRenderer.render(
                    search.getHtmlName(), config, "values"));
            options.addChild(tag);
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
            tag = TextRenderer.render(text, config);
            tagt.addChild(tag);
        }
        
        return tagt;
    }
    
    private static final void renderCloseMenuButton(XMLElement button,
            String menu, String name) {
        String open = name.concat("_openmenu");
        String close = name.concat("_closemenu");
        
        button.add("id", close);
        button.add("class", "button_ctxmenu_close");
        button.add("style", "display:none");
        button.add("onclick", new StringBuilder(
                setElementDisplay(menu, "none")).
                append(setElementDisplay(open, "inline")).
                append(setElementDisplay(close, "none")).toString());
        button.addInner("-");
    }
    
    private static final void renderOpenMenuButton(XMLElement button,
            String menu, String name) {
        String open = name.concat("_openmenu");
        String close = name.concat("_closemenu");
        
        button.add("id", open);
        button.add("class", "button_ctxmenu_open");
        button.add("style", "display:inline");
        button.add("onclick", new StringBuilder(
                setElementDisplayOfClass(".ctxmenu", "none")).
                append(setElementDisplayOfClass(
                        ".button_ctxmenu_close", "none")).
                append(setElementDisplayOfClass(
                        ".button_ctxmenu_open", "inline")).
                append(setElementDisplay(menu, "inline-block")).
                append(setElementDisplay(open, "none")).
                append(setElementDisplay(close, "inline")).toString());
        button.addInner("+");
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
            Renderer.renderContainer(tags, container, config);
        
        return tags;
    }
    
    private static final String setElementDisplay(String name, String value) {
        return new StringBuilder("setElementDisplay('").append(name).
                append("','").append(value).append("');").toString();
    }

    private static final String setElementDisplayOfClass(
            String style, String display) {
        return new StringBuilder("setElementDisplayOfClass('").
                append(style).append("','").append(display).append("');").
                toString();
    }
}
