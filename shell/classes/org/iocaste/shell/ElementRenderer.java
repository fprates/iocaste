package org.iocaste.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class ElementRenderer {
    private String msgtext;
    private Const msgtype;
    private String[] script;
    
    public ElementRenderer() {
        String line;
        List<String> lines = new ArrayList<String>();
        InputStream is = this.getClass().getResourceAsStream("/META-INF/shell.js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        
        try {
            while ((line = reader.readLine()) != null)
                lines.add(line);
            
            reader.close();
            is.close();
        } catch (IOException e) {
            new RuntimeException(e);
        }
        
        script = lines.toArray(new String[0]);
    }
    
    public final void setMessageType(Const msgtype) {
        this.msgtype = msgtype;
    }
    
    /**
     * 
     * @param text
     * @param element
     */
    private final void renderElement(List<String> text, Element element) {
        switch (element.getType()) {
        case FORM:
        case TABLE:
        case STANDARD_CONTAINER:
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
            
        case LINK:
            renderLink(text, (Link)element);
            
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
    
    /**
     * 
     * @param text
     * @param button
     */
    private final void renderButton(List<String> text, Button button) {
        String inputtext;
        String name = button.getName();
        
        if (!button.isSubmit())
            inputtext = "<input type=\"button\" name=\"";
        else
            inputtext = "<input type=\"submit\" name=\"";
        
        text.add(new StringBuffer(inputtext).append(name).
                append("\" value=\"").append(button.getText()).
                append("\" onClick=\"defineAction('").append(name).
                append("')\"/>").toString());
    }

    /**
     * 
     * @param text
     * @param container
     */
    private final void renderContainer(List<String> text, Container container) {
        Form form;
        
        switch (container.getType()) {
        case FORM:
            form = (Form)container;
            form.build();
            
            text.add("<form method=\"post\" action=\"index.html\">");
            renderElements(text, container.getElements());
            text.add("<input type=\"hidden\" name=\"action\" id=\"action\"/>");
            text.add("</form>");
            
            break;
            
        case TABLE:
            text.add("<table>");
            renderElements(text, container.getElements());
            text.add("</table>");
            
            break;
            
        case STANDARD_CONTAINER:
            text.add("<div>");
            renderElements(text, container.getElements());
            text.add("</div>");
            
            break;
        }
        
    }

    /**
     * 
     * @param text
     * @param link
     */
    private final void renderLink(List<String> text, Link link) {
        text.add(new StringBuffer("<a href=\"index.html?action=").
                append(link.getAction()).append("\">").append(link.getText()).
                append("</a>").toString());
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
        text.add(new StringBuffer("<p>").append(text_.getText()).append("</p>").
                toString());
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
        
        text.add(new StringBuffer(inputtext).append(textfield.getName()).
                append("\">").toString());
    }
    
    private final void renderJavaScript(List<String> text, String[] script) {
        text.add("<script type=\"text/javascript\">");
        for (String line : script)
            text.add(line);
        text.add("</script>");
    }
    
    /**
     * 
     * @param text
     * @param title
     */
    private final void renderHeader(List<String> text, String title) {
        text.add("<head>");
        text.add(new StringBuffer("<title>").append(title).
                append("</title>").toString());
        renderJavaScript(text, script);
        text.add("</head>");
    }
    
    /**
     * 
     * @param text
     */
    private final void renderMessage(List<String> text) {
        StringBuffer message;
        
        message = new StringBuffer("<div>");
        
        text.add("<div><p>");
        switch (msgtype) {
        case WARNING:
            message.append("Aviso: ");
        case ERROR:
            message.append("Erro: ");
        }
        
        text.add(message.append(msgtext).append("</p></div>").toString());
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

        if (msgtext != null)
            renderMessage(text);

        renderContainer(text, vdata.getContainer());
        text.add("</body>");
        text.add("</html>");

        msgtext = null;
        msgtype = Const.NONE;
        
        return text.toArray(new String[0]);
    }
    
    /**
     * 
     * @param msgtext
     */
    public void setMessageText(String msgtext) {
        this.msgtext = msgtext;
    }
}
