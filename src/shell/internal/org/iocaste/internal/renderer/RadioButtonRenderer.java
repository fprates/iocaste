package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.RadioButton;

public class RadioButtonRenderer extends AbstractElementRenderer<RadioButton> {

    public RadioButtonRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.RADIO_BUTTON);
    }

    @Override
    protected final XMLElement execute(RadioButton radiobutton, Config config) {
        XMLElement labeltag, rbtag, rbtexttag;
        String text;
        
        labeltag = new XMLElement("label");
        labeltag.add("class", radiobutton.getStyleClass());
        
        text = radiobutton.getHtmlName();
        rbtag = new XMLElement("input");
        rbtag.add("type", "radio");
        rbtag.add("name", radiobutton.getGroup().getName());
        rbtag.add("value", radiobutton.getHtmlName());
        rbtag.add("id", text);
        
        if (radiobutton.isSelected())
            rbtag.add("checked", "checked");
        
        addEvents(rbtag, radiobutton);
        labeltag.addChild(rbtag);
        
        text = radiobutton.getText();
        if (text != null) {
            rbtexttag = new XMLElement("span");
            rbtexttag.addInner(text);
            labeltag.addChild(rbtexttag);
        }
        
        return labeltag;
    }
}
