package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.TextArea;
import org.iocaste.shell.common.handlers.OnFocusHandler;

public class TextAreaRenderer extends AbstractElementRenderer<TextArea> {

    public TextAreaRenderer(HtmlRenderer renderers) {
        super(renderers, Const.TEXT_AREA);
    }

    protected final XMLElement execute(TextArea area, Config config)
            throws Exception {
        XMLElement areatag;
        String name, value;
        ActionEventHandler handler;

        if (!area.isVisible())
            return get(Const.PARAMETER).run(area, config);

        name = area.getHtmlName();
        value = area.get();
        areatag = new XMLElement("textarea");
        areatag.add("id", name);
        areatag.add("name", name);
        areatag.add("class", area.getStyleClass());
        areatag.add("cols", Integer.toString(area.getWidth()));
        areatag.add("rows", Integer.toString(area.getHeight()));
        
        if (!area.isEnabled())
            areatag.add("disabled", "disabled");
        
        handler = config.viewctx.getEventHandler(name, name, "focus");
        handler.name = name;
        handler.call = Shell.send(name, "&event=focus");
        area.put("focus", new OnFocusHandler(area));
        
        areatag.addInner((value == null)? "" : value);
        return areatag;
    }
}
