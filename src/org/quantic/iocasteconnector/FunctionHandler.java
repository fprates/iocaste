package org.quantic.iocasteconnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.common.External;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

import com.sap.conn.jco.AbapClassException;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

public class FunctionHandler implements JCoServerFunctionHandler {
    private Function function;
    private ExtendedObject portfunction;
    private External external;
    
    public FunctionHandler(Function function, External external,
            ExtendedObject portfunction) {
        this.function = function;
        this.portfunction = portfunction;
        this.external = external;
    }
    
    private final Map<String, Object> extract(
            Context context, JCoParameterList list) {
        Object value;
        String name;
        ExtendedObject functionitem;
        Iterator<JCoField> it;
        JCoField field;
        Map<String, Object> result;
        Map<String, ExtendedObject> structitems;
        
        if (list == null)
            return null;

        result = new HashMap<>();
        it = list.iterator();
        while (it.hasNext()) {
            field = it.next();
            name = field.getName();
            
            functionitem = context.items.get(name);
            switch (functionitem.geti("TYPE")) {
            case DataType.BOOLEAN:
                value = (list.getChar(name) == 'X');
                break;
            case DataType.CHAR:
                value = list.getString(name);
                break;
            case DataType.NUMC:
            case DataType.BYTE:
            case DataType.INT:
            case DataType.LONG:
            case DataType.SHORT:
                value = list.getBigDecimal(name);
                break;
            case DataType.DEC:
                value = list.getDouble(name);
                break;
            case DataType.DATE:
                value = list.getDate(name);
                break;
            case DataType.TIME:
                value = list.getTime(name);
                break;
            case DataType.EXTENDED:
                structitems = extractStructureItems(context.structures, name);
                
                value = extractStructure(
                        structitems, name, field.getStructure());
                break;
            case DataType.TABLE:
                value = extractTable(
                        context.structures, functionitem, field.getTable());
                break;
            default:
                value = null;
                break;
            }
            
            result.put(name, value);
        }
        
        return result;
    }
    
    private Map<String, Object> extractStructure(
            Map<String, ExtendedObject> structitems, String structname,
            JCoRecord sapstructure) {
        Iterator<JCoField> it;
        JCoField field;
        Object value;
        String name;
        ExtendedObject structitem;
        Map<String, Object> result;
        
        result = new HashMap<>();
        it = sapstructure.iterator();
        while (it.hasNext()) {
            field = it.next();
            name = field.getName();
            
            structitem = structitems.get(name);
            switch (structitem.geti("TYPE")) {
            case DataType.BOOLEAN:
                value = (sapstructure.getChar(name) == 'X');
                break;
            case DataType.CHAR:
                value = sapstructure.getString(name);
                break;
            case DataType.NUMC:
            case DataType.BYTE:
            case DataType.INT:
            case DataType.LONG:
            case DataType.SHORT:
                value = sapstructure.getBigDecimal(name);
                break;
            case DataType.DEC:
                value = sapstructure.getDouble(name);
                break;
            case DataType.DATE:
                value = sapstructure.getDate(name);
                break;
            case DataType.TIME:
                value = sapstructure.getTime(name);
                break;
            default:
                value = null;
                break;
            }
            
            result.put(name, value);
        }
        
        return result;
    }
    
    private Map<String, ExtendedObject> extractStructureItems(
            Map<String, ComplexDocument> structures, String name) {
        ExtendedObject[] objects;
        Map<String, ExtendedObject> structitems;
        
        objects = structures.get(name).getItems("items");
        if (objects == null)
            throw new RuntimeException("invalid function definition.");
        
        structitems = new HashMap<>();
        for (ExtendedObject object : objects)
            structitems.put(object.getst("NAME"), object);
        
        return structitems;
    }
    
    private List<Map<String, Object>> extractTable(
            Map<String, ComplexDocument> structures,
            ExtendedObject functionitem, JCoTable saptable) {
        String name, structname;
        Map<String, ExtendedObject> structitems;
        Map<String, Object> table;
        List<Map<String, Object>> tables;
        
        structname = functionitem.getst("STRUCTURE");
        name = functionitem.getst("NAME");
        structitems = extractStructureItems(structures, structname);
        tables = new ArrayList<>();
        do {
            table = extractStructure(structitems, name, saptable);
            tables.add(table);
        } while (saptable.nextRow());
        
        return tables;
    }

    @Override
    public void handleRequest(JCoServerContext sapcontext,
            JCoFunction sapfunction) throws AbapException, AbapClassException {
        Context context;
        JCoParameterList list;
        String servername, functionname, sapfunctionname, name;
        Message message;
        GenericService service;
        ComplexDocument functionmodel;
        Map<String, Object> extracted;
        ExtendedObject[] parameters;

        try {
            sapfunctionname = sapfunction.getName();
            log(sapfunctionname, " invoked.");
            
            servername = portfunction.getst("SERVICE");
            functionname = portfunction.getst("SERVICE_FUNCTION");
            if ((servername == null) || (functionname == null)) {
                log("server name and/or server function undefined.");
                return;
            }

            functionmodel = new Documents(function).
                    getComplexDocument("XTRNL_FUNCTION", null, sapfunctionname);
            
            parameters = functionmodel.getItems("parameters");
            if (parameters == null)
                throw new RuntimeException("failed recovering parameters.");

            context = new Context();
            for (ExtendedObject object : parameters) {
                name = object.getst("NAME");
                log("parameter ", name, " recovered.");
                context.items.put(name, object);
            }

            context.lists.put(
                    "importing", sapfunction.getImportParameterList());
            context.lists.put(
                    "exporting", sapfunction.getExportParameterList());
            context.lists.put(
                    "changing", sapfunction.getChangingParameterList());
            context.lists.put(
                    "tables", sapfunction.getTableParameterList());
            
            context.structures = external.
                    getFunctionStructures(sapfunctionname);
            
            message = new Message(functionname);
            message.add("function", sapfunctionname);
            message.add("parameters", context.result);
            for (String key : context.lists.keySet()) {
                list = context.lists.get(key);
                if (list == null)
                    continue;
                extracted = extract(context, list);
                for (String extkey : extracted.keySet())
                    context.result.put(extkey, extracted.get(extkey));
            }
            
            log("invoking ", functionname, "@", servername, "...");
            service = new GenericService(function, servername);
            context.result = service.invoke(message);
            if (context.result == null) {
                log("success.");
                return;
            }
            
            prepareToExport(context);
            
            log("success.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }

    @SuppressWarnings("unchecked")
    private final void moveTableToSAP(
            Map<String, ExtendedObject> structureitems, JCoTable saptable,
            Object from) {
        List<Map<String, Object>> lines;
        int type;
        Object value;
        
        saptable.deleteAllRows();
        lines = (List<Map<String, Object>>)from;
        for (Map<String, Object> line : lines) {
            saptable.appendRow();
            for (String key : line.keySet()) {
                type = structureitems.get(key).geti("TYPE");
                value = line.get(key);
                switch (type) {
                case DataType.DEC:
                    value = ExtendedObject.convertd(value);
                    break;
                case DataType.INT:
                    value = ExtendedObject.converti(value);
                    break;
                case DataType.LONG:
                case DataType.NUMC:
                    value = ExtendedObject.convertl(value);
                    break;
                case DataType.SHORT:
                    value = ExtendedObject.convertsh(value);
                    break;
                }
                
                saptable.setValue(key, value);
            }
        }
    }
    
    private final void log(Object... args) {
        StringBuilder sb;
        
        if (args.length == 1) {
            System.out.println(args[0]);
            return;
        }
        
        sb = new StringBuilder();
        for (Object arg : args)
            sb.append(arg);
        
        System.out.println(sb.toString());
    }
    
    private final void prepareToExport(Context context) {
        JCoParameterList list;
        boolean istable;
        String name, structurename;
        JCoField field;
        Iterator<JCoField> it;
        ExtendedObject functionitem;
        Map<String, ExtendedObject> structureitems;
        
        for (String keylist : new String[] {
                "exporting", "changing", "tables"
        }) {
            list = context.lists.get(keylist);
            if (list == null)
                continue;
            
            istable = keylist.equals("tables");
            it = list.iterator();
            while (it.hasNext()) {
                field = it.next();
                name = field.getName();
                if (!istable) {
                    list.setValue(name, (Object)context.result.get(name));
                    continue;
                }
                
                functionitem = context.items.get(name);
                structurename = functionitem.get("STRUCTURE");
                structureitems = extractStructureItems(
                        context.structures, structurename);
                moveTableToSAP(structureitems, field.getTable(),
                        context.result.get(name));
            }
        }
    }
}

class Context {
    public Map<String, Object> result;
    public Map<String, JCoParameterList> lists;
    public Map<String, ExtendedObject> items;
    public Map<String, ComplexDocument> structures;
    
    public Context() {
        items = new HashMap<>();
        lists = new HashMap<>();
        result = new HashMap<>();
    }
}
