package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.internal.EventHandler;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.ExpandBar;

public class ExpandBarRenderer extends AbstractElementRenderer<ExpandBar> {

    public ExpandBarRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.EXPAND_BAR);
    }

    @Override
    protected final XMLElement execute(ExpandBar container, Config config) {
        XMLElement ebarea, ebtag;
        List<XMLElement> ebtags;
        EventHandler handler;
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
        edge.setEventHandler(container.getEventHandler());
        edge.setEnabled(container.isEnabled());
        ebarea.addChild(get(Const.BUTTON).run(edge, config));
        
        handler = config.actionInstance(edge.getAction());
        handler.name = edge.getHtmlName();
        handler.event = "click";
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
