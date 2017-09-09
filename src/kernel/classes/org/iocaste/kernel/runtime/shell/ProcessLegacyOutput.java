package org.iocaste.kernel.runtime.shell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.factories.DataFormFactory;
import org.iocaste.kernel.runtime.shell.factories.ExpandBarFactory;
import org.iocaste.kernel.runtime.shell.factories.FileUploadFactory;
import org.iocaste.kernel.runtime.shell.factories.FormFactory;
import org.iocaste.kernel.runtime.shell.factories.FrameFactory;
import org.iocaste.kernel.runtime.shell.factories.LegacyButtonFactory;
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
import org.iocaste.kernel.runtime.shell.factories.TabbedPaneFactory;
import org.iocaste.kernel.runtime.shell.factories.TabbedPaneItemFactory;
import org.iocaste.kernel.runtime.shell.factories.TableToolFactory;
import org.iocaste.kernel.runtime.shell.factories.TextEditorFactory;
import org.iocaste.kernel.runtime.shell.factories.TextFactory;
import org.iocaste.kernel.runtime.shell.factories.TextFieldFactory;
import org.iocaste.kernel.runtime.shell.factories.TilesFactory;
import org.iocaste.kernel.runtime.shell.factories.VirtualControlFactory;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.legacy.LegacyHtmlRenderer;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.shell.common.View;

public class ProcessLegacyOutput extends AbstractHandler {
    public Map<ViewSpecItem.TYPES, SpecFactory> factories;

    public ProcessLegacyOutput() {
        factories = new HashMap<>();
        factories.put(ViewSpecItem.TYPES.BUTTON,
                new LegacyButtonFactory());
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
    }
    
    @Override
    public Object run(Message message) throws Exception {
        StringBuilder content;
        List<String> lines;
        Config config = new Config();
        View view = message.get("view");
        
        config.viewctx = new ViewContext(view);
        config.viewctx.types = RuntimeEngine.CONST_TYPES;
        config.viewctx.function = getFunction();
        config.viewctx.viewexport = new ViewExport();
        config.viewctx.viewexport.popupcontrol = message.getst("popupcontrol");
        config.viewctx.viewexport.title = view.getTitle();
        config.viewctx.factories = factories;
        config.logid = message.geti("logid");
        config.sequence = message.getl("sequence");
        config.username = message.getst("username");
        config.viewctx.function = getFunction();
        
        lines = new LegacyHtmlRenderer().run(config);
        content = new StringBuilder();
        for (String line : lines)
        	content.append(line);
        
        return content.toString().getBytes();
    }
    
}
