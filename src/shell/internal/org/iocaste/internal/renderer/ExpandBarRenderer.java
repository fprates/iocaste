package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.ExpandBar;

public class ExpandBarRenderer extends Renderer {

    public static final List<XMLElement> render(ExpandBar container,
            Config config) {
        XMLElement ebtag;
        String name = container.getName();
        String edgename = new StringBuilder(name).append(".edge").toString();
        Button edge = new Button(container, edgename);
        List<XMLElement> ebtags, tags = new ArrayList<>();
        String text = container.getText();
        
        edge.setText((text == null)? name : text);
        edge.setSubmit(false);
        edge.setStyleClass("eb_edge");
        edge.setEventHandler(container.getEventHandler());
        edge.addEvent("onClick", new StringBuilder("revertElementDisplay('").
                append(name).append("'); send ('").
                append(edgename).append("', null, null);").toString());
        
        tags.add(ButtonRenderer.render(edge, config));
        
        ebtag = new XMLElement("div");
        ebtags = new ArrayList<>();
        
        for (Element element : container.getElements()) {
            if (element.getName().equals(edgename) &&
                    element.getType() == Const.BUTTON)
                continue;
            
            Renderer.renderElement(ebtags, element, config);
        }
        
        ebtag.add("class", container.getStyleClass());
        ebtag.add("id", name);
        if (ebtags.size() == 0)
            ebtag.addInner("");
        else
            ebtag.addChildren(ebtags);
        
        tags.add(ebtag);
        
        if (!container.isExpanded())
            config.addOnload(new StringBuilder("setElementDisplay('").
                    append(name).append("', 'none');").toString());
        
        return tags;
    }
}
