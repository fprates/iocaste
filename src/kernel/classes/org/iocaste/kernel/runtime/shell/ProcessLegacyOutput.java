package org.iocaste.kernel.runtime.shell;

import java.util.List;

import org.iocaste.kernel.runtime.shell.renderer.LegacyNodeListItemRenderer;
import org.iocaste.kernel.runtime.shell.renderer.LegacyNodeListRenderer;
import org.iocaste.kernel.runtime.shell.renderer.LegacyTextRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.View;

public class ProcessLegacyOutput extends AbstractHandler {

	@Override
	public Object run(Message message) throws Exception {
	    StringBuilder content;
		HtmlRenderer renderer;
		List<String> lines;
		View view = message.get("view");
		ViewContext viewctx = new ViewContext(view);
		
		viewctx.function = getFunction();
		viewctx.title = view.getTitle();
		
		renderer = new HtmlRenderer();
		new LegacyNodeListRenderer(renderer);
		new LegacyNodeListItemRenderer(renderer);
		new LegacyTextRenderer(renderer);
		
		lines = renderer.run(viewctx);
		content = new StringBuilder();
		for (String line : lines)
			content.append(line);
		
		return content.toString().getBytes();
	}
	
}