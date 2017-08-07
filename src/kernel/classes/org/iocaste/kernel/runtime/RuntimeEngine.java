package org.iocaste.kernel.runtime;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.kernel.documents.Documents;
import org.iocaste.kernel.runtime.session.GetContextId;
import org.iocaste.kernel.runtime.session.IsConnected;
import org.iocaste.kernel.runtime.session.NewContext;
import org.iocaste.kernel.runtime.shell.ProcessInput;
import org.iocaste.kernel.runtime.shell.ProcessLegacyOutput;
import org.iocaste.kernel.runtime.shell.ProcessOutput;
import org.iocaste.kernel.runtime.shell.factories.ButtonFactory;
import org.iocaste.kernel.runtime.shell.factories.DataFormFactory;
import org.iocaste.kernel.runtime.shell.factories.ExpandBarFactory;
import org.iocaste.kernel.runtime.shell.factories.FileUploadFactory;
import org.iocaste.kernel.runtime.shell.factories.FormFactory;
import org.iocaste.kernel.runtime.shell.factories.FrameFactory;
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
import org.iocaste.kernel.session.Session;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class RuntimeEngine extends AbstractFunction {
    public Map<ViewSpecItem.TYPES, SpecFactory> factories;
	public Documents documents;
	public Session session;
	
	public RuntimeEngine() {
        factories = new HashMap<>();
        factories.put(ViewSpecItem.TYPES.BUTTON,
        		new ButtonFactory());
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

        export("context_id_get", new GetContextId());
        export("context_new", new NewContext());
        export("input_process", new ProcessInput());
        export("is_valid_context", new IsConnected());
        export("legacy_output_process", new ProcessLegacyOutput());
        export("output_process", new ProcessOutput());
        export("style_data_get", new GetStyleSheet());
	}
}

class GetStyleSheet extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Object[] objects;
        Message _message;
        ViewExport viewexport = new ViewExport();
        Function function = getFunction();

        _message = new Message("nc_data_get");
        objects = new GenericService(function,
                "/iocaste-appbuilder/services.html").invoke(_message);
        
        viewexport = new ViewExport();
        viewexport.stylesheet = (Object[][])objects[0];
        viewexport.styleconst = (Object[][])objects[3];
        viewexport.ncspec = (Object[][])objects[1];
        viewexport.ncconfig = (Object[])objects[2];
        return viewexport;
    }
    
}