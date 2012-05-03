package org.iocaste.shell.renderer;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StandardList;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public class Renderer {
    
    /**
     * 
     * @param tag
     * @param element
     */
    protected static void addEvents(XMLElement tag, Element element) {
        for (String name : element.getEventNames())
            tag.add(name, element.getEvent(name));
    }
    
    /**
     * 
     * @param tags
     * @param container
     * @param config
     */
    public static final void renderContainer(List<XMLElement> tags,
            Container container, Config config) {
        XMLElement xmltag;
        
        switch (container.getType()) {
        case FORM:
            xmltag = FormRenderer.render((Form)container, config);
            xmltag.addChildren(config.getToForm());
            config.clearToForm();
            
            tags.add(xmltag);
            break;
            
        case DATA_FORM:
            tags.addAll(DataFormRenderer.render((DataForm)container, config));
            break;
            
        case LIST:
            tags.add(StandardListRenderer.render((StandardList)container, config));
            break;
            
        case TABBED_PANE:
            tags.add(TabbedPaneRenderer.render((TabbedPane)container, config));
            break;
            
        case TABLE:
            tags.add(TableRenderer.render((Table)container, config));
            break;
            
        case FRAME:
            tags.add(FrameRenderer.render((Frame)container, config));
            break;
            
        case MENU:
        case STANDARD_CONTAINER:
            tags.add(StandardContainerRenderer.render(
                    (StandardContainer)container, config));
            break;
        default:
            break;
        }
        
    }
    
    /**
     * 
     * @param tags
     * @param element
     * @param config
     */
    protected static final void renderElement(List<XMLElement> tags,
            Element element, Config config) {
        if (!element.isVisible())
            return;
        
        switch (element.getType()) {
        case HTML_TAG:
            tags.add(HtmlTagRenderer.render((HtmlTag)element));
            break;
            
        case CHECKBOX:
            tags.add(CheckBoxRenderer.render((CheckBox)element));
            break;
        
        case MENU_ITEM:
            tags.add(MenuItemRenderer.render((MenuItem)element, config));
            break;
            
        case FILE_ENTRY:
            tags.add(FileEntryRenderer.render((FileEntry)element));
            break;
            
        case LIST_BOX:
            tags.add(ListBoxRenderer.render((ListBox)element));
            break;
            
        case TEXT:
            tags.add(TextRenderer.render((Text)element, config));
            break;
            
        case TEXT_FIELD:
            tags.addAll(TextFieldRenderer.render((TextField)element, config));
            break;
            
        case BUTTON:
            tags.add(ButtonRenderer.render((Button)element, config));
            break;
            
        case LINK:
            tags.add(LinkRenderer.render((Link)element, config));
            break;
        
        case PARAMETER:
            tags.add(ParameterRenderer.render((Parameter)element));
            break;
            
        case RADIO_BUTTON:
            tags.addAll(RadioButtonRenderer.render((RadioButton)element));
            break;
            
        default:
            if (element.isContainable())
                renderContainer(tags, (Container)element, config);
        }
    }
    
    /**
     * 
     * @param elements
     * @return
     */
    protected static final List<XMLElement> renderElements(Element[] elements,
            Config config) {
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        for (Element element : elements) 
            renderElement(tags, element, config);
        
        return tags;
    }
    
    /**
     * 
     * @param input
     * @return
     */
    protected static final String toString(InputComponent input) {
        Object value = input.get();
        DataElement element = Shell.getDataElement(input);
        
        if (element == null || value == null)
            return (String)value;
        
        return toString(value, element, input.getLocale(),
                input.isBooleanComponent());
    }
    
    /**
     * 
     * @param value
     * @param element
     * @param locale
     * @return
     */
    protected static final String toString(Object value, DataElement element,
            Locale locale, boolean boolconvert) {
        DateFormat dateformat;
        NumberFormat numberformat;
        
        switch (element.getType()) {
        case DataType.DEC:
            numberformat = NumberFormat.getNumberInstance(locale);
            
            return numberformat.format(value);
            
        case DataType.NUMC:
            if (element.getLength() < DataType.MAX_INT_LEN)
                return Integer.toString((Integer)value);
            else
                return Long.toString((Long)value);
            
        case DataType.DATE:
            dateformat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
            
            return dateformat.format(value);
            
        case DataType.BOOLEAN:
            if (boolconvert)
                return ((Boolean)value)? "on" : "off";
            else
                return Boolean.toString((Boolean)value);
        default:
            return (String)value;
        }
    }
}
