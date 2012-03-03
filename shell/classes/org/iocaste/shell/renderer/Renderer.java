package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

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
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StandardContainer;
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
    protected static void addAttributes(XMLElement tag, Element element) {
        for (String name : element.getAttributeNames())
            tag.add(name, element.getAttribute(name));
    }
    
    /**
     * 
     * @param tags
     * @param container
     */
    public static final void renderContainer(List<XMLElement> tags,
            Container container, Config config) {
        switch (container.getType()) {
        case FORM:
            tags.add(FormRenderer.render((Form)container, config));
            break;
            
        case DATA_FORM:
            tags.addAll(DataFormRenderer.render((DataForm)container, config));
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
        }
        
    }
    
    /**
     * 
     * @param tags
     * @param element
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
            tags.add(ListRenderer.render((ListBox)element));
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
    
}
