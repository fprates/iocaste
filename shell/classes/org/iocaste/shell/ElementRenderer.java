package org.iocaste.shell;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class ElementRenderer {
    
    private final void renderElements(List<String> text, Container container) {
        Element[] elements = container.getElements();
        
        for (Element element : elements) {
            switch (element.getType()) {
            case FORM:
                renderContainer(text, (Container)element);
                
                break;
                
            case TEXT_FIELD:
                text.add(renderTextField((TextField)element));
                
                break;
            }
        }
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
            renderElements(text, container);
            text.add("</form>");
        }
    }
    
    private final String renderTextField(TextField textfield) {
        String inputtext;
        
        if (textfield.getPassword())
            inputtext = "<input type=\"text\" name=\"";
        else
            inputtext = "<input type=\"password\" name=\"";
        
        return new StringBuffer(inputtext).append(textfield.getName())
                .append("\">").toString();
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
