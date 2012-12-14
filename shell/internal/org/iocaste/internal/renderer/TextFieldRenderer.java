package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DataElement;
import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Text;
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
        String tftext;
        Text text;
        SearchHelp search;
        DataElement dataelement = Shell.getDataElement(textfield);
        int length = (dataelement == null)? textfield.getLength() :
            dataelement.getLength();
        String name = textfield.getHtmlName(), value = toString(textfield);
        XMLElement spantag, inputtag = new XMLElement("input");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        if (value == null)
            value = "";
        
        if (!textfield.isSecret())
            inputtag.add("type", "text");
        else
            inputtag.add("type", "password");
        
        inputtag.add("name", name);
        inputtag.add("id", name);
        inputtag.add("size", Integer.toString(textfield.getVisibleLength()));
        inputtag.add("maxlength", Integer.toString(length));
        inputtag.add("value", value);
        inputtag.add("onfocus", new StringBuilder("send('").append(name).
                append("', '&event=onfocus', null)").toString());
        
        if (!textfield.isEnabled()) {
            inputtag.add("class", new StringBuilder(textfield.getStyleClass()).
                    append("_disabled").toString());
            inputtag.add("readonly", "readonly");
        } else {
            inputtag.add("class", textfield.getStyleClass());
        }
        
        addEvents(inputtag, textfield);
        tags.add(inputtag);
        search = textfield.getSearchHelp();
        if (search != null)
            tags.add(renderSearchHelp(search, config));
        
        if (textfield.isObligatory()) {
            spantag = new XMLElement("input");
            spantag.add("type", "button");
            spantag.add("class", "sh_button");
            spantag.add("value", "!");
            spantag.add("disabled", "disabled");
            tags.add(spantag);
        }
        
        tftext = textfield.getText();
        if (tftext != null) {
            text = new Text(null, "");
            text.setStyleClass("tftext");
            text.setText(tftext);
            tags.add(TextRenderer.render(text, config));
        }
        
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
        
        button.setStyleClass("sh_button");
        button.setText("?");
        
        return ButtonRenderer.render(button, config);
    }
}
