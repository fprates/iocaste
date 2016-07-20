package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Text;

public class TextRenderer extends AbstractElementRenderer<Text> {
    
    public TextRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.TEXT);
    }

    @Override
    protected final XMLElement execute(Text text, Config config) {
        String text_;
        XMLElement ptag;
        
        ptag = new XMLElement(text.getTag());
        ptag.add("id", text.getHtmlName());
        ptag.add("class", text.getStyleClass());

        addEvents(ptag, text);
        text_ = text.getText();
        ptag.addInner((text_ != null)? text_ : text.getName());
        
        return ptag;
    }
}
