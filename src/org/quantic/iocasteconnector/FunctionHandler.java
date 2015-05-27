package org.quantic.iocasteconnector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

import com.sap.conn.jco.AbapClassException;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

public class FunctionHandler implements JCoServerFunctionHandler {
    private Function function;
    private ExtendedObject portfunction;
    
    public FunctionHandler(Function function, ExtendedObject portfunction) {
        this.function = function;
        this.portfunction = portfunction;
    }
    
    private final Map<String, Object> extract(
            Map<String, ExtendedObject> items, JCoParameterList list) {
        Object value;
        String name;
        ExtendedObject functionitem;
        Iterator<JCoField> it;
        JCoField field;
        Map<String, Object> result;
        
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
            default:
                value = null;
                break;
            }
            
            result.put(name, value);
        }
        
        return result;
    }
    
    @Override
    public void handleRequest(JCoServerContext context, JCoFunction sapfunction)
            throws AbapException, AbapClassException {
        String servername, functionname, sapfunctionname, name;
        Message message;
        GenericService service;
        ComplexDocument functionmodel;
        Map<String, Object> importing, exporting, changing, tables;
        Map<String, ExtendedObject> items;
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
            
            log("extracting importing parameters...");
            importing = extract(items, sapfunction.getImportParameterList());
            log("extracting exporting parameters...");
            exporting = extract(items, sapfunction.getExportParameterList());
            log("extracting changing parameters...");
            changing = extract(items, sapfunction.getChangingParameterList());
            log("extracting tables parameters...");
            tables = extract(items, sapfunction.getTableParameterList());
            
            log("invoking ", functionname, "@", servername, "...");
            message = new Message(functionname);
            message.add("function", sapfunctionname);
            message.add("importing", importing);
            message.add("exporting", exporting);
            message.add("changing", changing);
            message.add("tables", tables);
            service = new GenericService(function, servername);
            service.invoke(message);
            log("success.");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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