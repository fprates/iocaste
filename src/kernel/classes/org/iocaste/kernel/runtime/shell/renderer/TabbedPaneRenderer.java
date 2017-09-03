package org.iocaste.kernel.runtime.shell.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.kernel.runtime.shell.elements.Button;
import org.iocaste.kernel.runtime.shell.elements.TabbedPane;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TabbedPaneItem;

public class TabbedPaneRenderer extends AbstractElementRenderer<TabbedPane> {
    
    public TabbedPaneRenderer(HtmlRenderer renderer) {
        super(renderer, Const.TABBED_PANE);
    }

    @Override
    protected final XMLElement execute(TabbedPane tabbedpane, Config config) {
        Button button;
        String classname, name, text, current, btname;
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
        buttonrenderer = get(Const.BUTTON);
        for (Element element : elements) {
            if (element.getType() != Const.TABBED_PANE_ITEM)
                continue;
            
            item = (TabbedPaneItem)element;
            name = item.getName();
            text = item.getText();
            
            classname = current.equals(name)?
                    "tp_button_focused" : "tp_button_unfocused";
            
            config.viewctx.instance(TYPES.BUTTON, btname = name.concat("_bt"));
            button = new Button(config.viewctx, btname);
            button.setText(text);
            button.setStyleClass(classname);
            button.setEventHandler(tabbedpane.getEventHandler());
            button.setEnabled(element.isEnabled());
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
    
    private final void renderHiddenInputs(
            List<XMLElement> tags, Container container, Config config) {
        InputComponent input;
        ParameterRenderer renderer = get(Const.PARAMETER);
        
        for (Element element : container.getElements()) {
            if (element.isContainable()) {
                renderHiddenInputs(tags, (Container)element, config);
                continue;
            }
            
            if (!element.isDataStorable())
                continue;

            input = (InputComponent)element;
            tags.add(renderer.execute(input, config));
        }
    }
}
