package org.iocaste.shell;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class ElementRenderer {
    
    /**
     * 
     * @param text
     * @param element
     */
    private final void renderElement(List<String> text, Element element) {
        switch (element.getType()) {
        case FORM:
        case TABLE:
            renderContainer(text, (Container)element);
            
            break;
            
        case TABLE_ITEM:
            renderTableItem(text, (TableItem)element);
            
            break;
            
        case TEXT:
            renderText(text, (Text)element);
            
            break;
            
        case TEXT_FIELD:
            renderTextField(text, (TextField)element);
            
            break;
            
        case BUTTON:
            renderButton(text, (Button)element);
            
            break;
        }
    }
    
    /**
     * 
     * @param text
     * @param elements
     */
    private final void renderElements(List<String> text, Element[] elements) {
        for (Element element : elements) 
            renderElement(text, element);
    }
    
    private final void renderButton(List<String> text, Button button) {
        String inputtext;
        
        if (!button.isSubmit())
            inputtext = "<input type=\"button\" name=\"";
        else
            inputtext = "<input type=\"submit\" name=\"";
        
        text.add(new StringBuffer(inputtext).append(button.getName())
                .append("\" value=\"").append(button.getText())
                .append("\"/>").toString());
    }

    /**
     * 
     * @param text
     * @param container
     */
    private final void renderContainer(List<String> text, Container container) {
        String line;
        Form form;
        
        switch (container.getType()) {
        case FORM:
            form = (Form)container;
            line = new StringBuffer("<form method=\"post\" action=\"index.html?action=").
                    append(form.getAction()).
                    append("\">").toString();
            
            text.add(line);
            renderElements(text, container.getElements());
            text.add("</form>");
            
            break;
            
        case TABLE:
            text.add("<table>");
            renderElements(text, container.getElements());
            text.add("</table>");
            
            break;
        }
    }
    
    /**
     * 
     * @param text
     * @param item
     */
    private final void renderTableItem(List<String> text, TableItem item) {
        text.add("<tr>");
        
        for (Element element : item.getElements()) {
            text.add("<td>");
            renderElement(text, element);
            text.add("</td>");
        }
        
        text.add("</tr>");
    }
    
    /**
     * 
     * @param text
     * @param text_
     */
    private final void renderText(List<String> text, Text text_) {
        text.add(new StringBuffer("<p>").append(text_.getText()).append("</p>")
                .toString());
    }
    
    /**
     * 
     * @param text
     * @param textfield
     */
    private final void renderTextField(List<String> text, TextField textfield) {
        String inputtext;
        
        if (!textfield.isPassword())
            inputtext = "<input type=\"text\" name=\"";
        else
            inputtext = "<input type=\"password\" name=\"";
        
        text.add(new StringBuffer(inputtext).append(textfield.getName())
                .append("\">").toString());
    }
    
    /**
     * 
     * @param text
     * @param title
     */
    private final void renderHeader(List<String> text, String title) {
        text.add("<head>");
        text.add(new StringBuffer("<title>").append(title)
                .append("</title>").toString());
        text.add("</head>");
    }
    
    /**
     * 
     * @param vdata
     * @return
     */
    public final String[] run(ViewData vdata) {
        List<String> text = new ArrayList<String>();
        
        text.add("<html>");
        renderHeader(text, vdata.getTitle());
        text.add("<body>");
        renderContainer(text, vdata.getContainer());
        text.add("</body>");
        text.add("</html>");
        
        return text.toArray(new String[0]);
    }
}
