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

import com.sap.conn.jco.AbapClassException;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRecord;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

public abstract class AbstractSAPFunctionHandler implements
        JCoServerFunctionHandler {
    protected static final boolean CONNECT = true;
    protected static final boolean DONT_CONNECT = false;
    protected External external;
    private boolean connect;
    
    public AbstractSAPFunctionHandler(External external, boolean connect) {
        this.external = external;
        this.connect = connect;
    }
    
    public static final void extract(Context context) {
        JCoParameterList list;
        Map<String, Object> extracted;

        context.result.clear();
        for (String key : context.lists.keySet()) {
            list = context.lists.get(key);
            if (list == null)
                continue;
            extracted = extract(context, list);
            for (String extkey : extracted.keySet())
                context.result.put(extkey, extracted.get(extkey));
        }
    }
    
    private static final Map<String, Object> extract(
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
            if (functionitem == null)
                continue;
//                throw new RuntimeException(new StringBuilder("Parameter ").
//                        append(name).append(" not configured.").toString());
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
    
    private static final Map<String, Object> extractStructure(
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
            if (structitem == null)
                continue;
            
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
    
    private static Map<String, ExtendedObject> extractStructureItems(
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
    
    private static final List<Map<String, Object>> extractTable(
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
    public final void handleRequest(JCoServerContext context,
            JCoFunction function) throws AbapException, AbapClassException {
        try {
            if (connect)
                external.connect();
            run(context, function);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AbapClassException(e);
        } finally {
            if (connect)
                external.disconnect();
        }
    }
    
    protected final void log(Object... args) {
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

    @SuppressWarnings("unchecked")
    private static final void moveTableToSAP(
            Map<String, ExtendedObject> structureitems, JCoTable saptable,
            Object from) {
        ExtendedObject object;
        List<Map<String, Object>> lines;
        int type;
        Object value;
        
        saptable.deleteAllRows();
        lines = (List<Map<String, Object>>)from;
        for (Map<String, Object> line : lines) {
            saptable.appendRow();
            for (String key : line.keySet()) {
                object = structureitems.get(key);
                if (object == null)
                    continue;
                type = object.geti("TYPE");
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

    public static final Map<String, ExtendedObject> getFunction(
            Function function, String sapfunctionname) {
        ComplexDocument functionmodel;
        Map<String, ExtendedObject> items;
        ExtendedObject[] parameters;
        String name;

        functionmodel = new Documents(function).
                getComplexDocument("XTRNL_FUNCTION", null, sapfunctionname);
        
        if (functionmodel == null)
            throw new RuntimeException(new StringBuilder(sapfunctionname).
                    append(" not registered.").toString());
        
        parameters = functionmodel.getItems("parameters");
        if (parameters == null)
            throw new RuntimeException("failed recovering parameters.");

        items = new HashMap<>();
        for (ExtendedObject object : parameters) {
            name = object.getst("NAME");
            items.put(name, object);
        }
        
        return items;
    }
    
    public static final void prepareToExport(Context context) {
        JCoParameterList list;
        boolean istable;
        String name, structurename;
        JCoField field;
        Iterator<JCoField> it;
        ExtendedObject functionitem;
        Map<String, ExtendedObject> structureitems;
        
        for (String keylist : new String[] {
                "importing", "changing", "tables"
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
                if (functionitem == null)
                    continue;
                structurename = functionitem.get("STRUCTURE");
                structureitems = extractStructureItems(
                        context.structures, structurename);
                moveTableToSAP(structureitems, field.getTable(),
                        context.result.get(name));
            }
        }
    }
    
	public static final void register(Command stream)
			throws Exception {
        ExtendedObject portdata;
        
        portdata = stream.portconfig.getHeader();
        stream.provider = new RFCDataProvider();
        stream.provider.setConfig(portdata, stream.locale);
        
        Environment.registerDestinationDataProvider(stream.provider);
        Environment.registerServerDataProvider(stream.provider);
        System.out.println("ok");

        System.out.print("bringing up iocaste listenners...");
        stream.destination = JCoDestinationManager.
        		getDestination(portdata.getst("PORT_NAME"));
	}
	
	protected abstract void run(JCoServerContext context, JCoFunction function)
	        throws Exception;
	
	public static final void unregister(Command stream) {
		Environment.unregisterServerDataProvider(stream.provider);
		Environment.unregisterDestinationDataProvider(stream.provider);
		stream.provider = null;
	}
    
    public static final void transfer(Context context, JCoFunction  sapfunction)
    {
        context.lists.clear();
        context.lists.put(
                "importing", sapfunction.getImportParameterList());
        context.lists.put(
                "exporting", sapfunction.getExportParameterList());
        context.lists.put(
                "changing", sapfunction.getChangingParameterList());
        context.lists.put(
                "tables", sapfunction.getTableParameterList());
    }

}
