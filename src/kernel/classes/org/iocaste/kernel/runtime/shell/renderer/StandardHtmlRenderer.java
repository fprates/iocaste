package org.iocaste.kernel.runtime.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.renderer.internal.AbstractHtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.ScreenLock;
import org.iocaste.kernel.runtime.shell.renderer.legacy.CanvasRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.CheckBoxRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.DataFormRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.DummyRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.ExpandBarRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.FileEntryRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.FrameRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.HtmlTagRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.ParameterRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.PrintAreaRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.StandardContainerRenderer;
import org.iocaste.kernel.runtime.shell.renderer.legacy.TextAreaRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;

public class StandardHtmlRenderer extends AbstractHtmlRenderer {
    
    public StandardHtmlRenderer() {
        new ButtonRenderer(this);
        new CanvasRenderer(this);
        new CheckBoxRenderer(this);
        new DataFormRenderer(this);
        new DataItemRenderer(this);
        new DummyRenderer(this);
        new DummyRenderer(this, Const.RADIO_GROUP);
        new ExpandBarRenderer(this);
        new FileEntryRenderer(this);
        new FormRenderer(this);
        new FrameRenderer(this);
        new HtmlTagRenderer(this);
        new LinkRenderer(this);
        new ListBoxRenderer(this);
        new MessageRenderer(this);
        new NodeListItemRenderer(this);
        new NodeListRenderer(this);
        new ParameterRenderer(this);
        new PrintAreaRenderer(this);
        new RadioButtonRenderer(this);
        new RangeFieldRenderer(this);
        new StandardContainerRenderer(this);
        new StandardContainerRenderer(this, Const.TABBED_PANE_ITEM);
        new TabbedPaneRenderer(this);
        new TableItemRenderer(this);
        new TableRenderer(this);
        new TextAreaRenderer(this);
        new TextFieldRenderer(this);
        new TextRenderer(this);
        new VirtualControlRenderer(this);
    }
    
    /**
     * Retorna visão renderizada.
     * @param view dados da visão
     * @return código html da visão
     */
    public final List<String> run(ViewContext viewctx) throws Exception {
        Config config;
        MessageRenderer messagerenderer;
        List<String> html = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();
        List<XMLElement> bodycontent = new ArrayList<>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        
        config = new Config();
        config.viewctx = viewctx;
        
        if (viewctx.offline) {
            for (Container container : viewctx.view.getContainers())
                getRenderer(container.getType()).run(
                        bodycontent, container, config);
            return null;
        }
        
        html.add("<!DOCTYPE html>");
        bodytag.add("onLoad", "initialize()");
        for (Container container : viewctx.view.getContainers())
            getRenderer(container.getType()).run(
                    bodycontent, container, config);
        
        messagerenderer = (MessageRenderer)getRenderer(Const.MESSAGE);
        bodytag.addChildren(bodycontent);
        bodytag.addChild(messagerenderer.run(null, config));
        bodytag.addChild(ScreenLock.render());
        tags.add(renderHeader(config));
        tags.add(bodytag);
        htmltag.addChildren(tags);
        html.add(htmltag.toString());
        
        return html;
    }
}
