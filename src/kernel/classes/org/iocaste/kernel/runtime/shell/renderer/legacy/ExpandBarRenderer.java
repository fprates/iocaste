package org.iocaste.kernel.runtime.shell.renderer.legacy;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.ExpandBar;

public class ExpandBarRenderer extends AbstractElementRenderer<ExpandBar> {

    public ExpandBarRenderer(HtmlRenderer renderers) {
        super(renderers, Const.EXPAND_BAR);
    }

    @Override
    protected final XMLElement execute(ExpandBar container, Config config)
            throws Exception {
        XMLElement ebarea, ebtag;
        List<XMLElement> ebtags;
        ActionEventHandler handler;
        String name = container.getName();
        String edgename = new StringBuilder(name).append(".edge").toString();
        Button edge = new Button(container, edgename);
        String text = container.getText();
        
        ebarea = new XMLElement("div");
        ebarea.add("class", container.getStyleClass());
        ebarea.add("id", container.getHtmlName().concat("_super"));
        
        edge.setText((text == null)? name : text);
        edge.setSubmit(false);
        edge.setStyleClass(container.getEdgeStyle());
        edge.put("click", container.getEventHandler("click"));
        edge.setEnabled(container.isEnabled());
        ebarea.addChild(get(Const.BUTTON).run(edge, config));

        handler = config.viewctx.
                getEventHandler(name, edge.getAction(), "click");
        handler.name = edge.getHtmlName();
        handler.call = new StringBuilder("revertElementDisplay('").
                append(name).append("'); send ('").
                append(edgename).append("', null, null);").toString();
        
        ebtag = new XMLElement("div");
        ebtags = new ArrayList<>();
        for (Element element : container.getElements()) {
            if (element.getName().equals(edgename) &&
                    element.getType() == Const.BUTTON)
                continue;
            ebtags.add(get(element.getType()).run(element, config));
        }
        
        ebtag.add("class", container.getInternalStyle());
        ebtag.add("id", name);
        if (ebtags.size() == 0)
            ebtag.addInner("");
        else
            ebtag.addChildren(ebtags);
        
        ebarea.addChild(ebtag);
        
        if (!container.isExpanded())
            config.addOnload(new StringBuilder("setElementDisplay('").
                    append(name).append("', 'none');").toString());
        
        return ebarea;
    }
}
