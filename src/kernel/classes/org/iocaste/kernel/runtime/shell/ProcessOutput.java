package org.iocaste.kernel.runtime.shell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.factories.SpecFactory;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Input;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MessageSource;

public class ProcessOutput extends AbstractHandler {
    private final Map<Const, TYPES> CONST_TYPES;
    
    public ProcessOutput() {
        CONST_TYPES = new HashMap<>();
        CONST_TYPES.put(Const.BUTTON, TYPES.BUTTON);
        CONST_TYPES.put(Const.DATA_FORM, TYPES.DATA_FORM);
        CONST_TYPES.put(Const.EXPAND_BAR, TYPES.EXPAND_BAR);
        CONST_TYPES.put(Const.FORM, TYPES.FORM);
        CONST_TYPES.put(Const.FILE_ENTRY, TYPES.FILE_UPLOAD);
        CONST_TYPES.put(Const.FRAME, TYPES.FRAME);
        CONST_TYPES.put(Const.LINK, TYPES.LINK);
        CONST_TYPES.put(Const.LIST_BOX, TYPES.LISTBOX);
        CONST_TYPES.put(Const.NODE_LIST, TYPES.NODE_LIST);
        CONST_TYPES.put(Const.NODE_LIST_ITEM, TYPES.NODE_LIST_ITEM);
        CONST_TYPES.put(Const.PRINT_AREA, TYPES.PRINT_AREA);
        CONST_TYPES.put(Const.RADIO_BUTTON, TYPES.RADIO_BUTTON);
        CONST_TYPES.put(Const.RADIO_GROUP, TYPES.RADIO_GROUP);
        CONST_TYPES.put(Const.STANDARD_CONTAINER, TYPES.STANDARD_CONTAINER);
        CONST_TYPES.put(Const.TABBED_PANE, TYPES.TABBED_PANE);
        CONST_TYPES.put(Const.TABBED_PANE_ITEM, TYPES.TABBED_PANE_ITEM);
        CONST_TYPES.put(Const.TABLE, TYPES.TABLE_TOOL);
        CONST_TYPES.put(Const.TEXT, TYPES.TEXT);
        CONST_TYPES.put(Const.TEXT_AREA, TYPES.TEXT_EDITOR);
        CONST_TYPES.put(Const.TEXT_FIELD, TYPES.TEXT_FIELD);
        CONST_TYPES.put(Const.PARAMETER, TYPES.PARAMETER);
        CONST_TYPES.put(Const.DATA_VIEW, TYPES.VIEW);
        CONST_TYPES.put(Const.VIRTUAL_CONTROL, TYPES.VIRTUAL_CONTROL);
    }
    
    public final void addEventHandlers(ViewContext viewctx) {
        SpecFactory factory;
        Element element;
        for (String key : viewctx.view.getElements().keySet()) {
            element = viewctx.view.getElement(key);
            factory = viewctx.function.factories.
                    get(CONST_TYPES.get(element.getType()));
            if (factory != null)
                factory.addEventHandler(viewctx, key);
        }
    }
    
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
        return ((RuntimeEngine)getFunction()).factories.get(type);
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
    	
    	lines = new HtmlRenderer().run(data.viewctx);
    	content = new StringBuilder();
    	for (String line : lines)
    		content.append(line);
    	return content.toString().getBytes();
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
    
    public final void run(ProcessOutputData outputdata) throws Exception {
        ComponentEntry entry;
        SpecFactory factory;
        Input input;
        ToolData data;
        
        outputdata.viewctx.function = getFunction();
        outputdata.viewctx.title = outputdata.viewexport.title;
        outputdata.viewctx.locale = outputdata.viewexport.locale.toString();
        outputdata.viewctx.parent = outputdata.parententry;
        if (!outputdata.noinitmessages)
            moveMessages(outputdata);
        if (outputdata.viewexport.subpages != null)
            for (int i = 0; i < outputdata.viewexport.subpages.length; i++)
                outputdata.viewctx.subpages.put(
                        (String)outputdata.viewexport.subpages[i][0],
                        (ViewExport)outputdata.viewexport.subpages[i][1]);
        
        for (Object object : outputdata.viewexport.items) {
            data = (ToolData)object;
            if (outputdata.viewexport.prefix == null) {
                outputdata.viewctx.add(data);
                continue;
            }
            
            data = data.clone(
                    outputdata.viewexport.prefix, outputdata.parententry.data);
            outputdata.viewctx.add(data);
        }
        
        for (String key : outputdata.viewctx.entries.keySet()) {
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
        
        if (!outputdata.noeventhandlers)
            addEventHandlers(outputdata.viewctx);
        
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
        
        input = new Input();
        input.viewctx = outputdata.viewctx;
        input.container = null;
//        input.pagectx.mpelements.clear();
        /*
         * deixa registerInputs() antes do commit(),
         * para que a conexão seja encerrada.
         */
        for (Container container : outputdata.viewctx.view.getContainers()) {
            input.element = container;
            input.register();
        }
    }
}
