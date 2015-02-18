package org.iocaste.internal.renderer;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public class TextFieldRenderer extends Renderer {


    public static final XMLElement render(DataItem dataitem,
            Config config) {
        return _render(dataitem, TextField.STYLE, config);
    }
    
    public static final XMLElement render(TextField textfield,
            Config config) {
        return _render(textfield, textfield.getStyleClass(),config);
    }
    
    /**
     * 
     * @param textfield
     * @param config
     * @return
     */
    private static final XMLElement _render(InputComponent input,
            String style, Config config) {
        StringBuilder sb;
        String tftext;
        Text text;
        SearchHelp search;
        XMLElement tagt, tagl, tagc;
        DataElement dataelement = Shell.getDataElement(input);
        int length = (dataelement == null)? input.getLength() :
            dataelement.getLength();
        String name = input.getHtmlName(), value = toString(input);
        XMLElement spantag, inputtag = new XMLElement("input");
        
        if (value == null)
            value = "";
        
        inputtag.add("type", (!input.isSecret())? "text" : "password");
        inputtag.add("name", name);
        inputtag.add("id", name);
        inputtag.add("size", Integer.toString(input.getVisibleLength()));
        inputtag.add("maxlength", Integer.toString(length));
        inputtag.add("value", value);
        inputtag.add("onfocus", new StringBuilder("send('").append(name).
                append("', '&event=onfocus', null)").toString());
        
        if (input.getContainer().getType() == Const.TABLE_ITEM)
            sb = new StringBuilder("table_cell_content");
        else
            sb = new StringBuilder(style);
        
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
        tagc = new XMLElement("td");
        tagc.addChild(inputtag);
        tagl = new XMLElement("tr");
        tagl.addChild(tagc);
        
        search = input.getSearchHelp();
        if (search != null) {
            tagc = new XMLElement("td");
            tagc.addChild(SHButtonRenderer.render(search, config));
            tagl.addChild(tagc);
        }
        
        if (input.isObligatory()) {
            spantag = new XMLElement("input");
            spantag.add("type", "button");
            spantag.add("class", "sh_button");
            spantag.add("value", "!");
            spantag.add("disabled", "disabled");
            tagc = new XMLElement("td");
            tagc.addChild(spantag);
            tagl.addChild(tagc);
        }
        
        tftext = input.getText();
        if (tftext != null) {
            text = new Text(config.getView(), "");
            text.setStyleClass("tftext");
            text.setText(tftext);
            tagc = new XMLElement("td");
            tagc.addChild(TextRenderer.render(text, config));
            tagl.addChild(tagc);
        }
        
        tagt = new XMLElement("table");
        tagt.addChild(tagl);
        return tagt;
    }
}
