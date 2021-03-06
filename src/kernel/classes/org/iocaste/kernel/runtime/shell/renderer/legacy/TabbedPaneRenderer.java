package org.iocaste.kernel.runtime.shell.renderer.legacy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.ButtonRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.View;

public class TabbedPaneRenderer extends AbstractElementRenderer<TabbedPane> {
    
    public TabbedPaneRenderer(HtmlRenderer renderers) {
        super(renderers, Const.TABBED_PANE);
    }

    @Override
    protected final XMLElement execute(TabbedPane tabbedpane, Config config)
            throws Exception {
        View view;
        Button button;
        String classname, name, text, current;
        TabbedPaneItem item;
        List<XMLElement> tags;
        Set<Element> elements;
        ButtonRenderer buttonrenderer;
        StandardContainerRenderer itemrenderer;
        XMLElement tabbedtag = new XMLElement("div");
        
        tabbedtag.add("id", tabbedpane.getName());
        tabbedtag.add("class", tabbedpane.getStyleClass());
        
        elements = tabbedpane.getElements();
        current = tabbedpane.getCurrentPage();
        view = config.getView();
        buttonrenderer = get(Const.BUTTON);
        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;
            
            item = (TabbedPaneItem)element;
            name = item.getName();
            text = item.getText();
            
            classname = current.equals(name)?
                    "tp_button_focused" : "tp_button_unfocused";
            
            button = new Button(view, name.concat("_bt"));
            button.setText(text);
            button.setStyleClass(classname);
            button.setEnabled(element.isEnabled());
            button.put("click", tabbedpane.getEventHandler("click"));
            tabbedtag.addChild(buttonrenderer.run(button, config));
        }

        tags = new ArrayList<>();
        itemrenderer = get(Const.TABBED_PANE_ITEM);
        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;

            item = (TabbedPaneItem)element;
            if (!item.getName().equals(current)) {
                renderHiddenInputs(tags, item, config);
                continue;
            }
            
            itemrenderer.run(tags, item, config);
        }

        tabbedtag.addChildren(tags);
        return tabbedtag;
    }
}