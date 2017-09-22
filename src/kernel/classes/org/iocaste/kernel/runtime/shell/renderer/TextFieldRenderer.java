package org.iocaste.kernel.runtime.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Calendar;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.tooldata.Text;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.runtime.shell.PopupData;
import org.iocaste.kernel.runtime.shell.PopupRenderer;
import org.iocaste.kernel.runtime.shell.ProcessInput;
import org.iocaste.kernel.runtime.shell.calendar.CalendarRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Source;
import org.iocaste.kernel.runtime.shell.renderer.legacy.ContextMenu;
import org.iocaste.kernel.runtime.shell.renderer.textfield.TextFieldContainerSource;
import org.iocaste.kernel.runtime.shell.renderer.textfield.TextFieldSource;
import org.iocaste.kernel.runtime.shell.renderer.textfield.TextFieldTableItemSource;
import org.iocaste.kernel.runtime.shell.sh.SearchHelpRenderer;

public class TextFieldRenderer extends AbstractElementRenderer<InputComponent>
{
    private PopupRenderer calendar, search;
    
    public TextFieldRenderer(HtmlRenderer renderer) {
        super(renderer, Const.TEXT_FIELD);
        put(Const.DATA_ITEM, new TextFieldSource());
        put(Const.TEXT_FIELD, new TextFieldSource());
        put(Const.TABLE_ITEM, new TextFieldTableItemSource());
        put(new TextFieldContainerSource());
        calendar = new CalendarRenderer();
        search = new SearchHelpRenderer();
    }

    private final boolean allowContextMenu(InputComponent input) {
        boolean required = input.isObligatory();
        boolean calendar = (input.isEnabled() && (input.getCalendar() != null));
        boolean tftext = (input.getText() != null);
        boolean search = (input.getSearchHelp() != null);
        
        return (required || tftext || search || calendar);
    }
    
    @Override
    protected final XMLElement execute(InputComponent input, Config config)
            throws Exception {
    	ToolData tooldata;
        Container container;
        StringBuilder sb;
        String tftext, name, value, label, cellstyle;
        Text text;
        XMLElement tagt, inputtag, tag;
        DataElement dataelement;
        ActionEventHandler handler;
        Source source;
        boolean enabled;
        int length;
        
        if (!input.isVisible())
            return get(Const.PARAMETER).run(input, config);
        
        source = getSource(input.getType());
        source.set("input", input);
        label = (String)source.run();
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
        
        handler = config.viewctx.getEventHandler(name, name, "focus");
        handler.name = name;
        handler.call = new StringBuilder("_send('").append(name).
                append("', '&event=onfocus', null);").toString();
        
        container = input.getContainer();
        source = (container != null)?
                getSource(container.getType()) : getSource();
        source.set("sb", sb = new StringBuilder());
        source.set("style", getStyle(input));
        cellstyle = (String)source.run();
        
        if (!(enabled = input.isEnabled())) {
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
        tagt.add("class", cellstyle);
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

        inputtag.add("size", Integer.toString(input.getVisibleLength()));
        inputtag.add("class", sb.toString());
        if (!allowContextMenu(input)) {
            tag.add("style", "display:inline;");
            return tagt;
        }
        
        if (enabled)
            renderContext(config, input, tagt, tag);

        tftext = input.getText();
        if (tftext != null) {
        	tooldata = new ToolData(TYPES.TEXT);
        	tooldata.name = tftext;
            text = new Text(config.viewctx, tooldata);
            text.setTag("li");
            text.setStyleClass("tftext");
            tag = get(Const.TEXT).run(text, config);
            tagt.addChild(tag);
        }
        
        return tagt;
    }
    
    private final void renderContext(Config config, InputComponent input,
            XMLElement tagt, XMLElement tag) throws Exception {
        ContextMenu ctxmenu;
        boolean required;
        SearchHelp search;
        Calendar calendar;
        PopupControl popupcontrol;
        String calname, shname;
        View view = config.viewctx.view;
        
        tag.add("style", "display:inline;float:left;");
        ctxmenu = new ContextMenu(
                config, "input.options", tagt, input.getHtmlName());
        required = input.isObligatory();
        if (required)
            ctxmenu.add(ProcessInput.getMessage(view, "required"));

        popupcontrol = config.viewctx.view.
                getElement(config.viewctx.viewexport.popupcontrol);
        calendar = input.getCalendar();
        if (calendar != null) {
            ctxmenu.add(calendar.getHtmlName(), "calendar");
            if (popupcontrol != null) {
                calname = popupcontrol.getName();
                if ((calname.equals(calendar.getEarly()) ||
                     calname.equals(calendar.getLate()) ||
                     calname.equals(calendar.getName()))) {
                    tag = new XMLElement("li");
                    tag.addChildren(
                            renderPopup(config, this.calendar, popupcontrol));
                    tagt.addChild(tag);
                }
            }
        }

        search = input.getSearchHelp();
        if (search != null) {
            ctxmenu.add(search.getHtmlName(), "values");
            if (popupcontrol != null) {
                shname = popupcontrol.getHtmlName();
                if (shname.equals(search.getHtmlName()) ||
                    shname.equals(search.getChild())) {
                    tag = new XMLElement("li");
                    tag.addChildren(
                            renderPopup(config, this.search, popupcontrol));
                    tagt.addChild(tag);
                }
            }
        }
    }
    
    private final List<XMLElement> renderPopup(Config config,
            PopupRenderer renderer, PopupControl control) throws Exception {
        PopupData data;
        List<XMLElement> tags;

        data = new PopupData();
        data.viewctx = config.viewctx;
        data.control = control;
        data.action = config.currentaction;
        data.form = config.currentform;
        data.connection = config.viewctx.function.documents.database.
                getDBConnection(config.viewctx.sessionid);
        renderer.run(data);
        
        get(data.container.getType()).run(
                tags = new ArrayList<>(), data.container, config);
        return tags;
    }
}
