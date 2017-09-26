package org.iocaste.kernel.runtime.shell;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.factories.ButtonFactory;
import org.iocaste.kernel.runtime.shell.factories.CheckBoxFactory;
import org.iocaste.kernel.runtime.shell.factories.DataFormFactory;
import org.iocaste.kernel.runtime.shell.factories.ExpandBarFactory;
import org.iocaste.kernel.runtime.shell.factories.FileUploadFactory;
import org.iocaste.kernel.runtime.shell.factories.FormFactory;
import org.iocaste.kernel.runtime.shell.factories.FrameFactory;
import org.iocaste.kernel.runtime.shell.factories.LinkFactory;
import org.iocaste.kernel.runtime.shell.factories.ListBoxFactory;
import org.iocaste.kernel.runtime.shell.factories.NodeListFactory;
import org.iocaste.kernel.runtime.shell.factories.NodeListItemFactory;
import org.iocaste.kernel.runtime.shell.factories.ParameterFactory;
import org.iocaste.kernel.runtime.shell.factories.PrintAreaFactory;
import org.iocaste.kernel.runtime.shell.factories.RadioButtonFactory;
import org.iocaste.kernel.runtime.shell.factories.RadioGroupFactory;
import org.iocaste.kernel.runtime.shell.factories.ReportToolFactory;
import org.iocaste.kernel.runtime.shell.factories.SpecFactory;
import org.iocaste.kernel.runtime.shell.factories.StandardContainerFactory;
import org.iocaste.kernel.runtime.shell.factories.TabbedPaneFactory;
import org.iocaste.kernel.runtime.shell.factories.TabbedPaneItemFactory;
import org.iocaste.kernel.runtime.shell.factories.TableToolFactory;
import org.iocaste.kernel.runtime.shell.factories.TextEditorFactory;
import org.iocaste.kernel.runtime.shell.factories.TextFactory;
import org.iocaste.kernel.runtime.shell.factories.TextFieldFactory;
import org.iocaste.kernel.runtime.shell.factories.TilesFactory;
import org.iocaste.kernel.runtime.shell.factories.VirtualControlFactory;
import org.iocaste.kernel.runtime.shell.renderer.StandardHtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Input;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.HeaderLink;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewSpecItem;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class ProcessOutput extends AbstractHandler {
    public Map<ViewSpecItem.TYPES, SpecFactory> factories;
    private StandardHtmlRenderer renderer;
    
    public ProcessOutput() {
        factories = new HashMap<>();
        factories.put(ViewSpecItem.TYPES.BUTTON,
                new ButtonFactory());
        factories.put(ViewSpecItem.TYPES.CHECK_BOX,
                new CheckBoxFactory());
        factories.put(ViewSpecItem.TYPES.DATA_FORM,
                new DataFormFactory());
        factories.put(ViewSpecItem.TYPES.EXPAND_BAR,
                new ExpandBarFactory());
        factories.put(ViewSpecItem.TYPES.FORM,
                new FormFactory());
        factories.put(ViewSpecItem.TYPES.FILE_UPLOAD,
                new FileUploadFactory());
        factories.put(ViewSpecItem.TYPES.FRAME,
                new FrameFactory());
        factories.put(ViewSpecItem.TYPES.LINK,
                new LinkFactory());
        factories.put(ViewSpecItem.TYPES.LISTBOX,
                new ListBoxFactory());
        factories.put(ViewSpecItem.TYPES.NODE_LIST,
                new NodeListFactory());
        factories.put(ViewSpecItem.TYPES.NODE_LIST_ITEM,
                new NodeListItemFactory());
        factories.put(ViewSpecItem.TYPES.PRINT_AREA,
                new PrintAreaFactory());
        factories.put(ViewSpecItem.TYPES.RADIO_BUTTON,
                new RadioButtonFactory());
        factories.put(ViewSpecItem.TYPES.RADIO_GROUP,
                new RadioGroupFactory());
        factories.put(ViewSpecItem.TYPES.REPORT_TOOL,
                new ReportToolFactory());
        factories.put(ViewSpecItem.TYPES.STANDARD_CONTAINER,
                new StandardContainerFactory());
        factories.put(ViewSpecItem.TYPES.TABBED_PANE,
                new TabbedPaneFactory());
        factories.put(ViewSpecItem.TYPES.TABBED_PANE_ITEM,
                new TabbedPaneItemFactory());
        factories.put(ViewSpecItem.TYPES.TABLE_TOOL,
                new TableToolFactory());
        factories.put(ViewSpecItem.TYPES.TEXT,
                new TextFactory());
        factories.put(ViewSpecItem.TYPES.TEXT_EDITOR,
                new TextEditorFactory());
        factories.put(ViewSpecItem.TYPES.TEXT_FIELD,
                new TextFieldFactory());
        factories.put(ViewSpecItem.TYPES.TILES,
                new TilesFactory());
        factories.put(ViewSpecItem.TYPES.PARAMETER,
                new ParameterFactory());
        factories.put(ViewSpecItem.TYPES.VIEW,
                null);
        factories.put(ViewSpecItem.TYPES.VIRTUAL_CONTROL,
                new VirtualControlFactory());
        
        renderer = new StandardHtmlRenderer();
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
            handler.execute(viewctx, entry.data.name);
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
        return factories.get(type);
    }
    
    private final void moveMessages(ProcessOutputData data) {
        Map<String, String> messages;
        
        data.viewctx.messagesrc = new MessageSource();
        data.viewctx.messagesrc.instance(data.viewctx.locale);
        messages = ProcessInput.msgsource.get(data.viewctx.locale);
        for (String key : messages.keySet())
            data.viewctx.messagesrc.put(key, messages.get(key));
        if (data.viewctx.viewexport.message != null)
            data.viewctx.messagetext = data.viewctx.messagesrc.get(
                    data.viewctx.viewexport.message,
                    data.viewctx.viewexport.message);
        data.viewctx.messageargs = data.viewctx.viewexport.msgargs;
        data.viewctx.messagetype = data.viewctx.viewexport.msgtype;
    }

    @Override
    public Object run(Message message) throws Exception {
        StringBuilder content;
        List<String> lines;
        ProcessOutputData data = new ProcessOutputData();
        
        data.viewctx = new ViewContext();
        data.viewctx.viewexport = message.get("view");
        data.viewctx.sessionid = message.getSessionid();
        run(data);
        
        lines = renderer.run(data.viewctx);
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
        outputdata.viewctx.locale =
                outputdata.viewctx.viewexport.locale.toString();
        outputdata.viewctx.noeventhandlers = outputdata.noeventhandlers;
        outputdata.viewctx.types = RuntimeEngine.CONST_TYPES;
        outputdata.viewctx.factories = factories;
        if (!outputdata.noinitmessages)
            moveMessages(outputdata);
        if (outputdata.viewctx.viewexport.subpages != null)
            for (int i = 0;
                    i < outputdata.viewctx.viewexport.subpages.length; i++)
                outputdata.viewctx.subpages.put(
                    (String)outputdata.viewctx.viewexport.subpages[i][0],
                    (ViewExport)outputdata.viewctx.viewexport.subpages[i][1]);

        // se for houver prefixo, processa apenas elementos prefixados.
        if (outputdata.viewctx.viewexport.prefix == null)
            elements = outputdata.viewctx.entries.keySet();
        else
            elements = new LinkedHashSet<>();
        for (Object object : outputdata.viewctx.viewexport.items) {
            data = (ToolData)object;
            if (outputdata.viewctx.viewexport.prefix == null) {
                outputdata.viewctx.add(data);
                continue;
            }
            
            data = data.clone(
                    outputdata.viewctx.viewexport.prefix,
                    outputdata.parententry.data);
            outputdata.viewctx.add(data);
            elements.add(data.name);
        }
        
        for (String key : elements) {
            entry = outputdata.viewctx.entries.get(key);
            build(outputdata.viewctx,
                    entry, outputdata.viewctx.viewexport.prefix);
            factory = factories.get(entry.data.type);
            if (factory == null)
                continue;
            factory.generate(outputdata.viewctx,
                    entry, outputdata.viewctx.viewexport.prefix);
            if (entry.component == null)
                continue;
            entry.component.run();
            entry.component.refresh();
        }
        
        outputdata.viewctx.view.setLocale(
                outputdata.viewctx.viewexport.locale);
        if ((outputdata.viewctx.viewexport.messages != null) &&
                (outputdata.viewctx.viewexport.messages.length > 0)) {
            outputdata.viewctx.messagesrc.importMessages(
                outputdata.viewctx.locale,
                outputdata.viewctx.viewexport.messages);
            fillTranslations(outputdata.viewctx);
        }
        
        outputdata.viewctx.view.setStyleSheet(
                outputdata.viewctx.viewexport.stylesheet);
        outputdata.viewctx.view.setStyleConst(
                outputdata.viewctx.viewexport.styleconst);
        for (HeaderLink link : outputdata.viewctx.viewexport.links)
            outputdata.viewctx.view.add(link);
        
        input = new Input(outputdata);
//        input.pagectx.mpelements.clear();
        /*
         * deixa registerInputs() antes do commit(),
         * para que a conexão seja encerrada.
         */
        for (Container container : outputdata.viewctx.view.getContainers())
            input.register(container, false);
    }
}
