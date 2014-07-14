package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public class TextFieldRenderer extends Renderer {


    public static final List<XMLElement> render(DataItem dataitem,
            Config config) {
        return _render(dataitem, config);
    }
    
    public static final List<XMLElement> render(TextField textfield,
            Config config) {
        return _render(textfield, config);
    }
    
    /**
     * 
     * @param textfield
     * @param config
     * @return
     */
    private static final List<XMLElement> _render(InputComponent input,
            Config config) {
        StringBuilder sb;
        String tftext;
        Text text;
        SearchHelp search;
        DataElement dataelement = Shell.getDataElement(input);
        int length = (dataelement == null)? input.getLength() :
            dataelement.getLength();
        String name = input.getHtmlName(), value = toString(input);
        XMLElement spantag, inputtag = new XMLElement("input");
        List<XMLElement> tags = new ArrayList<XMLElement>();
        
        if (value == null)
            value = "";
        
        if (!input.isSecret())
            inputtag.add("type", "text");
        else
            inputtag.add("type", "password");
        
        inputtag.add("name", name);
        inputtag.add("id", name);
        inputtag.add("size", Integer.toString(input.getVisibleLength()));
        inputtag.add("maxlength", Integer.toString(length));
        inputtag.add("value", value);
        inputtag.add("onfocus", new StringBuilder("send('").append(name).
                append("', '&event=onfocus', null)").toString());
        
        sb = new StringBuilder(input.getStyleClass());
        if (!input.isEnabled()) {
            sb.append("_disabled");
            inputtag.add("readonly", "readonly");
        }
        
        if (dataelement != null)
            switch (dataelement.getType()) {
            case DataType.NUMC:
            case DataType.DEC:
                sb.append("_right");
                break;
            }
        
        inputtag.add("class", sb.toString());
        
        addEvents(inputtag, input);
        tags.add(inputtag);
        search = input.getSearchHelp();
        if (search != null)
            tags.add(renderSearchHelp(search, config));
        
        if (input.isObligatory()) {
            spantag = new XMLElement("input");
            spantag.add("type", "button");
            spantag.add("class", "sh_button");
            spantag.add("value", "!");
            spantag.add("disabled", "disabled");
            tags.add(spantag);
        }
        
        tftext = input.getText();
        if (tftext != null) {
            text = new Text(config.getView(), "");
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
