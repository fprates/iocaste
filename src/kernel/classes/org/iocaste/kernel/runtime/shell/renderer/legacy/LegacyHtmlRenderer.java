package org.iocaste.kernel.runtime.shell.renderer.legacy;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.kernel.runtime.shell.renderer.ButtonRenderer;
import org.iocaste.kernel.runtime.shell.renderer.DataItemRenderer;
import org.iocaste.kernel.runtime.shell.renderer.LinkRenderer;
import org.iocaste.kernel.runtime.shell.renderer.MessageRenderer;
import org.iocaste.kernel.runtime.shell.renderer.RangeFieldRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TableItemRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TableRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TextFieldRenderer;
import org.iocaste.kernel.runtime.shell.renderer.TextRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.AbstractHtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.ScreenLock;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;

public class LegacyHtmlRenderer extends AbstractHtmlRenderer {
    
    public LegacyHtmlRenderer() {
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
    
    private final String composePageTrack(Config config) {
        return new StringBuffer(config.viewctx.view.getAppName()).append(".").
                append(config.viewctx.view.getPageName()).append(":").
                append(config.viewctx.sessionid).append(":").
                append(config.logid).append(":").
                append(config.sequence).toString();
    }
    
    /**
     * Retorna visão renderizada.
     * @param view dados da visão
     * @return código html da visão
     */
    public final List<String> run(Config config) throws Exception {
        MessageRenderer messagerenderer;
        List<String> html = new ArrayList<>();
        List<XMLElement> tags = new ArrayList<>();
        List<XMLElement> bodycontent = new ArrayList<>();
        XMLElement htmltag = new XMLElement("html");
        XMLElement bodytag = new XMLElement("body");
        
        config.setPageTrack(composePageTrack(config));
        config.setPopupControl(config.viewctx.viewexport.popupcontrol);
        
        html.add("<!DOCTYPE html>");
        bodytag.add("onLoad", "initialize()");
        for (Container container : config.viewctx.view.getContainers())
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
