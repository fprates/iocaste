package org.iocaste.kernel.runtime;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;
import org.iocaste.kernel.documents.Documents;
import org.iocaste.kernel.runtime.shell.ProcessInput;
import org.iocaste.kernel.runtime.shell.ProcessLegacyOutput;
import org.iocaste.kernel.runtime.shell.ProcessOutput;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

public class RuntimeEngine extends AbstractFunction {
    public static final Map<Const, TYPES> CONST_TYPES;
    public Documents documents;
    
    static {
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
	
	public RuntimeEngine() {
        export("input_process", new ProcessInput());
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