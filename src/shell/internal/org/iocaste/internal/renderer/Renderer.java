package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Canvas;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.ExpandBar;
import org.iocaste.shell.common.FileEntry;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.HtmlTag;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.PrintArea;
import org.iocaste.shell.common.RadioButton;
import org.iocaste.shell.common.RangeField;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextArea;
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
            config.setCurrentForm(container.getHtmlName());
            xmltag = FormRenderer.render((Form)container, config);
//            xmltag.addChildren(config.getToForm());
//            config.clearToForm();
            
            tags.add(xmltag);
            break;
            
        case DATA_FORM:
            tags.addAll(DataFormRenderer.render((DataForm)container, config));
            break;
            
        case TABBED_PANE:
            tags.add(TabbedPaneRenderer.render((TabbedPane)container, config));
            break;
            
        case TABBED_PANE_ITEM:
            tags.add(StandardContainerRenderer.render(
                    (TabbedPaneItem)container, config));
            break;
            
        case TABLE:
            tags.add(TableRenderer.render((Table)container, config));
            break;
            
        case FRAME:
            tags.add(FrameRenderer.render((Frame)container, config));
            break;
            
        case EXPAND_BAR:
            tags.add(ExpandBarRenderer.render((ExpandBar)container, config));
            break;
        
        case NODE_LIST:
            tags.add(NodeListRenderer.render((NodeList)container, config));
            break;
            
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
    public static final void renderElement(List<XMLElement> tags,
            Element element, Config config) {
        if (!element.isVisible())
            return;
        
        switch (element.getType()) {
        case BUTTON:
            tags.add(ButtonRenderer.render((Button)element, config));
            break;
            
        case CANVAS:
            tags.add(CanvasRenderer.render((Canvas)element, config));
            break;
            
        case CHECKBOX:
            tags.add(CheckBoxRenderer.render((CheckBox)element));
            break;
            
        case FILE_ENTRY:
            tags.add(FileEntryRenderer.render((FileEntry)element));
            break;
            
        case HTML_TAG:
            tags.add(HtmlTagRenderer.render((HtmlTag)element));
            break;
            
        case LINK:
            tags.addAll(LinkRenderer.render((Link)element, config));
            break;
            
        case LIST_BOX:
            tags.add(ListBoxRenderer.render((ListBox)element));
            break;
            
        case PARAMETER:
            tags.add(ParameterRenderer.render((Parameter)element));
            break;
            
        case PRINT_AREA:
            tags.add(PrintAreaRenderer.render((PrintArea)element));
            break;
            
        case RADIO_BUTTON:
            tags.addAll(RadioButtonRenderer.render((RadioButton)element,
                    config));
            break;
        
        case RANGE_FIELD:
            tags.add(RangeFieldRenderer.render((RangeField)element, config));
            break;
            
        case TEXT:
            tags.add(TextRenderer.render((Text)element, config));
            break;
            
        case TEXT_AREA:
            tags.add(TextAreaRenderer.render((TextArea)element, config));
            break;
            
        case TEXT_FIELD:
            tags.add(
                    TextFieldRenderer.render((TextField)element, null, config));
            break;
            
        default:
            if (element.isContainable())
                renderContainer(tags, (Container)element, config);
        }
    }
    
    /**
     * 
     * @param elements
     * @param config
     * @return
     */
    protected static final List<XMLElement> renderElements(
            Set<Element> elements, Config config) {
        List<XMLElement> tags = new ArrayList<>();
        
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
        
        return Shell.toString(value, element, input.getLocale(),
                input.isBooleanComponent());
    }
}
