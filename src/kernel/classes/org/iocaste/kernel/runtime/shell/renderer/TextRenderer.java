package org.iocaste.kernel.runtime.shell.renderer;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.MaskFormatter;

import org.iocaste.kernel.runtime.shell.elements.Text;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;

public class TextRenderer extends AbstractElementRenderer<Text> {
    private Map<String, MaskFormatter> formatters;
    
    public TextRenderer(HtmlRenderer renderer) {
        super(renderer, Const.TEXT);
        formatters = new HashMap<>();
    }

    @Override
    protected final XMLElement execute(Text text, Config config) {
        String text_;
        XMLElement ptag;
        
        ptag = new XMLElement(text.getTag());
        ptag.add("id", text.getHtmlName());
        ptag.add("class", text.getStyleClass());

        addAttributes(ptag, text);
        text_ = text.getText();
        ptag.addInner((text_ != null)? mask(text, text_) : text.getName());
        
        return ptag;
    }
    
    private final String mask(Text text, String value) {
        MaskFormatter formatter;
        String mask = text.getMask();
        if (mask == null)
            return value;
        formatter = formatters.get(mask);
        try {
            if (formatter == null) {
                formatter = new MaskFormatter(mask);
                formatters.put(mask, formatter);
            }
            formatter.setValueContainsLiteralCharacters(false);
            return formatter.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
