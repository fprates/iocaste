package org.iocaste.workbench.project.datadict;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class DataElementAdd extends AbstractCommand {
    private Map<String, Integer> types;
    
    public DataElementAdd() {
        required("name");
        required("type");
        optional("size");
        optional("decimals");
        optional("upcase", "true", "false");
        
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
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        ExtendedObject object;
        ExtendedObject[] objects;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        objects = extcontext.project.getItems("dataelement");
        object = readobjects(objects, "NAME", name);
        if (object != null) {
            message(Const.ERROR, "data element %s already exists.", name);
            return;
        }
        
        object = extcontext.project.instance("dataelement");
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("NAME", name);
        object.set("TYPE", types.get(parameters.get("type")));
        object.set("SIZE", parameters.get("size"));
        object.set("DECIMALS", parameters.get("decimals"));
        object.set("UPCASE", getBooleanParameter("upcase"));
        save("project", extcontext.project);
        print("data element %s updated.", name);
    }

}
