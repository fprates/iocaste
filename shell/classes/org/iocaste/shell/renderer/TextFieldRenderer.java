package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DataElement;
import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.TextField;

public class TextFieldRenderer extends Renderer {
    
    /**
     * 
     * @param textfield
     * @param config
     * @return
     */
    public static final List<XMLElement> render(TextField textfield,
            Config config) {
        SearchHelp search;
        DataElement dataelement = Shell.getDataElement(textfield);
        int length = (dataelement == null)?textfield.getLength() :
            dataelement.getLength();
        String name = textfield.getHtmlName(), value = toString(textfield);
        XMLElement spantag, inputtag = new XMLElement("input");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        if (value == null)
            value = "";
        
        if (!textfield.isPassword())
            inputtag.add("type", "text");
        else
            inputtag.add("type", "password");
        
        inputtag.add("name", name);
        inputtag.add("class", textfield.getStyleClass());
        inputtag.add("id", name);
        inputtag.add("size", Integer.toString(length));
        inputtag.add("maxlength", Integer.toString(length));
        inputtag.add("value", value);
        
        if (!textfield.isEnabled())
            inputtag.add("readonly", "readonly");

        addEvents(inputtag, textfield);
        
        tags.add(inputtag);
        
        if (textfield.isObligatory()) {
            spantag = new XMLElement("span");
            spantag.addInner("*");
            tags.add(spantag);
        }
        
        search = textfield.getSearchHelp();
        if (search != null)
            tags.add(renderSearchHelp(search, config));
        
        return tags;
    }
    
    /**
     * 
     * @param sh
     * @return
     */
    private static final XMLElement renderSearchHelp(SearchHelp sh,
            Config config) {
        Button button = new Button(null, sh.getHtmlName());
        
        button.setText("?");
        
        return ButtonRenderer.render(button, config);
    }
}
