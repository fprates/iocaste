package org.iocaste.kernel.runtime.shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.factories.ButtonFactory;
import org.iocaste.kernel.runtime.shell.factories.CheckBoxFactory;
import org.iocaste.kernel.runtime.shell.factories.DataFormFactory;
import org.iocaste.kernel.runtime.shell.factories.ExpandBarFactory;
import org.iocaste.kernel.runtime.shell.factories.FileUploadFactory;
import org.iocaste.kernel.runtime.shell.factories.FormFactory;
import org.iocaste.kernel.runtime.shell.factories.FrameFactory;
import org.iocaste.kernel.runtime.shell.factories.LegacyTabbedPaneFactory;
import org.iocaste.kernel.runtime.shell.factories.LegacyTabbedPaneItemFactory;
import org.iocaste.kernel.runtime.shell.factories.LegacyTextFactory;
import org.iocaste.kernel.runtime.shell.factories.LegacyVirtualControlFactory;
import org.iocaste.kernel.runtime.shell.factories.LinkFactory;
import org.iocaste.kernel.runtime.shell.factories.ListBoxFactory;
import org.iocaste.kernel.runtime.shell.factories.NavControlFactory;
import org.iocaste.kernel.runtime.shell.factories.NodeListFactory;
import org.iocaste.kernel.runtime.shell.factories.NodeListItemFactory;
import org.iocaste.kernel.runtime.shell.factories.ParameterFactory;
import org.iocaste.kernel.runtime.shell.factories.PrintAreaFactory;
import org.iocaste.kernel.runtime.shell.factories.RadioButtonFactory;
import org.iocaste.kernel.runtime.shell.factories.RadioGroupFactory;
import org.iocaste.kernel.runtime.shell.factories.ReportToolFactory;
import org.iocaste.kernel.runtime.shell.factories.SpecFactory;
import org.iocaste.kernel.runtime.shell.factories.StandardContainerFactory;
import org.iocaste.kernel.runtime.shell.factories.TableToolFactory;
import org.iocaste.kernel.runtime.shell.factories.TextEditorFactory;
import org.iocaste.kernel.runtime.shell.factories.TextFieldFactory;
import org.iocaste.kernel.runtime.shell.factories.TilesFactory;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.legacy.LegacyHtmlRenderer;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.tooldata.ViewSpecItem;

public class ProcessLegacyOutput extends AbstractHandler {
    public Map<ViewSpecItem.TYPES, SpecFactory> factories;

    public ProcessLegacyOutput() {
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
        factories.put(ViewSpecItem.TYPES.PAGE_CONTROL,
                new NavControlFactory());
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
                new LegacyTabbedPaneFactory());
        factories.put(ViewSpecItem.TYPES.TABBED_PANE_ITEM,
                new LegacyTabbedPaneItemFactory());
        factories.put(ViewSpecItem.TYPES.TABLE_TOOL,
                new TableToolFactory());
        factories.put(ViewSpecItem.TYPES.TEXT,
                new LegacyTextFactory());
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
                new LegacyVirtualControlFactory());
    }
    
    @Override
    public Object run(Message message) throws Exception {
        StringBuilder content;
        List<String> lines;
        Object[] outgoingevent;
        ActionEventHandler event;
        List<Object[]> eventslist;
        Map<String, ActionEventHandler> events;
        Map<String, Map<String, ActionEventHandler>> actions;
        Config config = new Config();
        View view = message.get("view");
        
        config.viewctx = new ViewContext(view);
        config.viewctx.types = RuntimeEngine.CONST_TYPES;
        config.viewctx.function = getFunction();
        config.viewctx.viewexport = new ViewExport();
        config.viewctx.viewexport.popupcontrol = message.getst("popupcontrol");
        config.viewctx.viewexport.title = view.getTitle();
        config.viewctx.viewexport.contexturl = message.getst("contexturl");
        config.viewctx.factories = factories;
        config.viewctx.sessionid = message.getst("sessionid");
        config.logid = message.geti("logid");
        config.sequence = message.getl("sequence");
        config.username = message.getst("username");
        config.viewctx.function = getFunction();
        
        lines = new LegacyHtmlRenderer().run(config);
        content = new StringBuilder();
        for (String line : lines)
        	content.append(line);

        eventslist = new ArrayList<>();
        for (String elementkey : config.viewctx.actions.keySet()) {
            actions = config.viewctx.actions.get(elementkey);
            for (String actionkey : actions.keySet()) {
                events = actions.get(actionkey);
                for (String eventkey : events.keySet()) {
                    event = events.get(eventkey);
                    outgoingevent = new Object[5];
                    outgoingevent[0] = event.call;
                    outgoingevent[1] = event.event;
                    outgoingevent[2] = event.name;
                    outgoingevent[3] = event.submit;
                    outgoingevent[4] = actionkey;
                    eventslist.add(outgoingevent);
                }
            }
        }
        
        return new Object[] {
                content.toString().getBytes(),
                eventslist.toArray(new Object[0]),
                config.viewctx.view
        };
    }
    
}
