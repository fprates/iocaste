package org.iocaste.shell;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
//import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class ElementRenderer {
    
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
        }
    }
    
    private final void renderElements(List<String> text, Element[] elements) {
        for (Element element : elements) 
            renderElement(text, element);
    }
    
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
    
    private final void renderTableItem(List<String> text, TableItem item) {
        text.add("<tr>");
        
        for (Element element : item.getElements()) {
            text.add("<td>");
            renderElement(text, element);
            text.add("</td>");
        }
        
        text.add("</tr>");
    }
    
    private final void renderText(List<String> text, Text text_) {
        text.add(new StringBuffer("<p>").append(text_.getText()).append("</p>")
                .toString());
    }
    
    private final void renderTextField(List<String> text, TextField textfield) {
        String inputtext;
        
        if (textfield.getPassword())
            inputtext = "<input type=\"text\" name=\"";
        else
            inputtext = "<input type=\"password\" name=\"";
        
        text.add(new StringBuffer(inputtext).append(textfield.getName())
                .append("\">").toString());
    }
    
    private final void renderHeader(List<String> text, String title) {
        text.add("<head>");
        text.add(new StringBuffer("<title>").append(title)
                .append("</title>").toString());
        text.add("</head>");
    }
    
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
