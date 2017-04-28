package org.iocaste.kernel.runtime.shell;

import java.util.List;
import java.util.Map;

import org.iocaste.kernel.runtime.Runtime;
import org.iocaste.kernel.runtime.shell.factories.SpecFactory;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Input;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MessageSource;

public class ProcessOutput extends AbstractHandler {
    
    private void build(ViewContext viewctx, ComponentEntry entry, String prefix) {
        SpecFactory factory;
        SpecItemHandler handler;
        
        factory = getFactory(entry.data.type);
        if (factory == null)
            return;
        handler = factory.getHandler();
        if (handler != null)
            handler.execute((Runtime)getFunction(), entry.data.name);
        factory.run(viewctx, entry, prefix);
    }
    
    private final void fillTranslations(ViewContext viewctx) {
        String text;
        Map<String, Element> elements;
        
        elements = viewctx.view.getElements();
        for (String name : elements.keySet())
            elements.get(name).translate(viewctx.messagesrc);
        
        if (viewctx.title.text == null)
            return;
        text = viewctx.messagesrc.get(viewctx.title.text);
        if (text == null)
            return;
        if ((viewctx.title.args == null) || (viewctx.title.args.length == 0))
            viewctx.title.text = text;
        else
            viewctx.title.text = String.format(text,viewctx.title.args);
    }
    
    private final SpecFactory getFactory(TYPES type) {
        return ((Runtime)getFunction()).factories.get(type);
    }

	@Override
	public Object run(Message message) throws Exception {
		StringBuilder content;
		HtmlRenderer renderer;
		List<String> lines;
		ViewExport viewexport = message.get("view");
		ViewContext viewctx = run(viewexport);
		
		renderer = new HtmlRenderer();
		lines = renderer.run(viewctx);
		content = new StringBuilder();
		for (String line : lines)
			content.append(line);
		
		viewexport.items = null;
		viewexport.links = null;
		viewexport.locale = null;
		viewexport.messages = null;
		viewexport.prefix = null;
		viewexport.reqparameters = null;
		viewexport.styleconst = null;
		viewexport.stylesheet = null;
		return content.toString().getBytes();
	}
	
	public final ViewContext run(ViewExport viewexport) {
		ComponentEntry entry;
        SpecFactory factory;
        Input input;
		ViewContext viewctx = new ViewContext();
		
		viewctx.function = getFunction();
		viewctx.title = viewexport.title;
		
		for (Object object : viewexport.items)
			viewctx.add((ToolData)object);
		
		for (String key : viewctx.entries.keySet()) {
            entry = viewctx.entries.get(key);
            build(viewctx, entry, null);
            factory = viewctx.function.factories.get(entry.data.type);
            if (factory == null)
                continue;
            factory.generate(viewctx, entry, viewexport.prefix);
            if (entry.component == null)
                continue;
            entry.component.run();
            entry.component.refresh();
		}

		if ((viewexport.messages != null) && (viewexport.messages.length > 0)) {
			viewctx.view.setLocale(viewexport.locale);
			viewctx.messagesrc = new MessageSource();
			viewctx.messagesrc.importMessages(
					viewexport.locale.toString(), viewexport.messages);
            fillTranslations(viewctx);
		}
		
		viewctx.view.setStyleSheet(viewexport.stylesheet);
		viewctx.view.setStyleConst(viewexport.styleconst);
		for (HeaderLink link : viewexport.links)
			viewctx.view.add(link);

        input = new Input();
        input.viewctx = viewctx;
        input.container = null;
//        input.pagectx.mpelements.clear();
        /*
         * deixa registerInputs() antes do commit(),
         * para que a conexão seja encerrada.
         */
        for (Container container : viewctx.view.getContainers()) {
            input.element = container;
            input.register();
        }
		
		return viewctx;
	}
}
