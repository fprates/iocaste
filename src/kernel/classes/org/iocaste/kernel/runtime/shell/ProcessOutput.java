package org.iocaste.kernel.runtime.shell;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.factories.SpecFactory;
import org.iocaste.kernel.runtime.shell.renderer.StandardHtmlRenderer;
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
    
    private void build(
            ViewContext viewctx, ComponentEntry entry, String prefix) {
        SpecFactory factory;
        SpecItemHandler handler;
        
        factory = getFactory(entry.data.type);
        if (factory == null)
            return;
        handler = factory.getHandler();
        if (handler != null)
            handler.execute((RuntimeEngine)getFunction(), entry.data.name);
        factory.run(viewctx, entry, prefix);
    }
    
    private final void fillTranslations(ViewContext viewctx) {
        String text;
        Map<String, Element> elements;
        
        elements = viewctx.view.getElements();
        for (String name : elements.keySet())
            elements.get(name).translate(viewctx.messagesrc);
        
        if (viewctx.viewexport.title.text == null)
            return;
        text = viewctx.messagesrc.get(viewctx.viewexport.title.text);
        if (text == null)
            return;
        if ((viewctx.viewexport.title.args == null) ||
                (viewctx.viewexport.title.args.length == 0))
            viewctx.viewexport.title.text = text;
        else
            viewctx.viewexport.title.text =
                String.format(text,viewctx.viewexport.title.args);
    }
    
    private final SpecFactory getFactory(TYPES type) {
        return ((RuntimeEngine)getFunction()).factories.get(type);
    }
    
    private final void moveMessages(ProcessOutputData data) {
        Map<String, String> messages;
        
        data.viewctx.messagesrc = new MessageSource();
        data.viewctx.messagesrc.instance(data.viewctx.locale);
        messages = ProcessInput.msgsource.get(data.viewctx.locale);
        for (String key : messages.keySet())
            data.viewctx.messagesrc.put(key, messages.get(key));
        if (data.viewexport.message != null)
            data.viewctx.messagetext = data.viewctx.messagesrc.get(
                    data.viewexport.message, data.viewexport.message);
        data.viewctx.messageargs = data.viewexport.msgargs;
        data.viewctx.messagetype = data.viewexport.msgtype;
    }

	@Override
    public Object run(Message message) throws Exception {
    	StringBuilder content;
    	List<String> lines;
        ProcessOutputData data = new ProcessOutputData();
        
    	data.viewexport = message.get("view");
    	data.viewctx = new ViewContext();
        data.viewctx.sessionid = message.getSessionid();
    	run(data);
    	
    	lines = new StandardHtmlRenderer().run(data.viewctx);
    	content = new StringBuilder();
    	for (String line : lines)
    		content.append(line);
    	return content.toString().getBytes();
    }
    
    public final void run(ProcessOutputData outputdata) throws Exception {
        ComponentEntry entry;
        SpecFactory factory;
        Input input;
        ToolData data;
        Set<String> elements;
        
        outputdata.viewctx.function = getFunction();
        outputdata.viewctx.viewexport = outputdata.viewexport;
        outputdata.viewctx.locale = outputdata.viewexport.locale.toString();
        outputdata.viewctx.noeventhandlers = outputdata.noeventhandlers;
        outputdata.viewctx.types = RuntimeEngine.CONST_TYPES;
        if (!outputdata.noinitmessages)
            moveMessages(outputdata);
        if (outputdata.viewexport.subpages != null)
            for (int i = 0; i < outputdata.viewexport.subpages.length; i++)
                outputdata.viewctx.subpages.put(
                        (String)outputdata.viewexport.subpages[i][0],
                        (ViewExport)outputdata.viewexport.subpages[i][1]);

        // se for houver prefixo, processa apenas elementos prefixados.
        if (outputdata.viewexport.prefix == null)
            elements = outputdata.viewctx.entries.keySet();
        else
            elements = new LinkedHashSet<>();
        for (Object object : outputdata.viewexport.items) {
            data = (ToolData)object;
            if (outputdata.viewexport.prefix == null) {
                outputdata.viewctx.add(data);
                continue;
            }
            
            data = data.clone(
                    outputdata.viewexport.prefix, outputdata.parententry.data);
            outputdata.viewctx.add(data);
            elements.add(data.name);
        }
        
        for (String key : elements) {
            entry = outputdata.viewctx.entries.get(key);
            build(outputdata.viewctx, entry, outputdata.viewexport.prefix);
            factory = outputdata.viewctx.function.factories.
                    get(entry.data.type);
            if (factory == null)
                continue;
            factory.generate(
                    outputdata.viewctx, entry, outputdata.viewexport.prefix);
            if (entry.component == null)
                continue;
            entry.component.run();
            entry.component.refresh();
        }
        
        outputdata.viewctx.view.setLocale(outputdata.viewexport.locale);
        if ((outputdata.viewexport.messages != null) &&
                (outputdata.viewexport.messages.length > 0)) {
            outputdata.viewctx.messagesrc.importMessages(
                    outputdata.viewctx.locale, outputdata.viewexport.messages);
            fillTranslations(outputdata.viewctx);
        }
        
        outputdata.viewctx.view.setStyleSheet(
                outputdata.viewexport.stylesheet);
        outputdata.viewctx.view.setStyleConst(
                outputdata.viewexport.styleconst);
        for (HeaderLink link : outputdata.viewexport.links)
            outputdata.viewctx.view.add(link);
        
        input = new Input(outputdata, null);
//        input.pagectx.mpelements.clear();
        /*
         * deixa registerInputs() antes do commit(),
         * para que a conexão seja encerrada.
         */
        for (Container container : outputdata.viewctx.view.getContainers())
            input.register(container, false);
    }
}
