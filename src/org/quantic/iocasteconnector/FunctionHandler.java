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
    
    private final Map<String, Object> extract(Map<String, ExtendedObject> items,
            Map<String, ComplexDocument> structures, JCoParameterList list) {
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
            
            functionitem = items.get(name);
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
                structitems = extractStructureItems(structures, name);
                
                value = extractStructure(
                        structitems, name, field.getStructure());
                break;
            case DataType.TABLE:
                value = extractTable(
                        structures, functionitem, field.getTable());
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
    public void handleRequest(JCoServerContext context, JCoFunction sapfunction)
            throws AbapException, AbapClassException {
        boolean istable;
        Map<String, Object> result;
        JCoParameterList list;
        JCoField field;
        Iterator<JCoField> it;
        String servername, functionname, sapfunctionname, name;
        Message message;
        GenericService service;
        ComplexDocument functionmodel;
        Map<String, Object> extracted;
        Map<String, ExtendedObject> items;
        Map<String, ComplexDocument> structures;
        Map<String, JCoParameterList> lists;
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

            items = new HashMap<>();
            for (ExtendedObject object : parameters) {
                name = object.getst("NAME");
                log("parameter ", name, " recovered.");
                items.put(name, object);
            }

            lists = new HashMap<>();
            lists.put("importing", sapfunction.getImportParameterList());
            lists.put("exporting", sapfunction.getExportParameterList());
            lists.put("changing", sapfunction.getChangingParameterList());
            lists.put("tables", sapfunction.getTableParameterList());
            
            structures = external.getFunctionStructures(sapfunctionname);
            
            message = new Message(functionname);
            message.add("function", sapfunctionname);
            message.add("parameters", result = new HashMap<>());
            for (String key : lists.keySet()) {
                list = lists.get(key);
                if (list == null)
                    continue;
                extracted = extract(items, structures, list);
                for (String extkey : extracted.keySet())
                    result.put(extkey, extracted.get(extkey));
            }
            
            log("invoking ", functionname, "@", servername, "...");
            service = new GenericService(function, servername);
            result = service.invoke(message);
            if (result == null) {
                log("success.");
                return;
            }
            
            for (String keylist : new String[] {
                    "exporting", "changing", "tables"
            }) {
                list = lists.get(keylist);
                if (list == null)
                    continue;
                
                istable = keylist.equals("tables");
                it = list.iterator();
                while (it.hasNext()) {
                    field = it.next();
                    name = field.getName();
                    if (!istable) {
                        list.setValue(name, (Object)result.get(name));
                        continue;
                    }
                    
                    moveTableToSAP(field.getTable(), result.get(name));
                }
            }
            
            log("success.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }

    @SuppressWarnings("unchecked")
    private final void moveTableToSAP(JCoTable saptable,
            Object from) {
        List<Map<String, Object>> lines;
        
        saptable.deleteAllRows();
        lines = (List<Map<String, Object>>)from;
        for (Map<String, Object> line : lines) {
            saptable.appendRow();
            for (String key : line.keySet())
                saptable.setValue(key, line.get(key));
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
}