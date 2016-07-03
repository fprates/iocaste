package org.iocaste.workbench.project.datadict;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;

public class DataElementAdd extends AbstractCommand {
    private Map<String, Integer> types;
    
    public DataElementAdd(Context extcontext) {
        super("data-element-add", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        required("type", "TYPE");
        optional("size", "SIZE");
        optional("decimals", "DECIMALS");
        optionalbl("upcase", "UPCASE");
        
        types = new HashMap<>();
        types.put("char", DataType.CHAR);
        types.put("date", DataType.DATE);
        types.put("dec", DataType.DEC);
        types.put("numc", DataType.NUMC);
        types.put("time", DataType.TIME);
        types.put("boolean", DataType.BOOLEAN);
        types.put("byte", DataType.BYTE);
        types.put("int", DataType.INT);
        types.put("long", DataType.LONG);
        types.put("short", DataType.SHORT);
        
        actionctx = getActionContext();
        actionctx.updateviewer = new DataElementUpdate();
    }
    
    @Override
    protected final Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject object;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        object = getObject("WB_DATA_ELEMENTS", name);
        if (object != null) {
            message(Const.ERROR, "data.element.exists");
            return null;
        }
        
        object = instance("WB_DATA_ELEMENTS");
        object.set("PROJECT", extcontext.project.getstKey());
        autoset(object);
        save(object);
        message(Const.STATUS, "dataelement.updated");
        return object;
    }
}
