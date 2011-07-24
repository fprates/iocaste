package org.iocaste.shell;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.TextField;

public class ElementRenderer {
    
    private final List<String> renderElements(Container container) {
        Element[] elements = container.getElements();
        List<String> lines = new ArrayList<String>();
        
        for (Element element : elements) {
            switch (element.getType()) {
            case FORM:
                lines.addAll(renderContainer((Container)element));
                
                break;
                
            case TEXT_FIELD:
                lines.add(renderTextField((TextField)element));
                
                break;
            }
        }
        
        return lines;
    }
    
    private final List<String> renderContainer(Container container) {
        String line;
        Form form;
        List<String> text = new ArrayList<String>();
        
        switch (container.getType()) {
        case FORM:
            form = (Form)container;
            line = new StringBuffer("<form method=\"post\" action=\"index.html?action=").
                    append(form.getAction()).
                    append("\">").toString();
            
            text.add(line);
            text.addAll(renderElements(container));
            text.add("</form>");
        }
        
        return text;
    }
    
    private final String renderTextField(TextField textfield) {
        return null;
    }
    
    public final String[] run(Container container) {
        return renderContainer(container).toArray(new String[0]);
    }
}
