package org.quantic.iocasteconnector;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.common.External;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

import com.sap.conn.jco.AbapClassException;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.server.JCoServerContext;

public class FunctionHandler extends AbstractSAPFunctionHandler {
    private Function function;
    private ExtendedObject portfunction;
    private External external;
    
    public FunctionHandler(Function function, External external,
            ExtendedObject portfunction) {
        this.function = function;
        this.portfunction = portfunction;
        this.external = external;
    }
    
    @Override
    public void handleRequest(JCoServerContext sapcontext,
            JCoFunction sapfunction) throws AbapException, AbapClassException {
        Context context;
        String servername, functionname, sapfunctionname;
        Message message;
        GenericService service;

        try {
            external.connect();
            sapfunctionname = sapfunction.getName();
            log(sapfunctionname, " invoked.");
            
            servername = portfunction.getst("SERVICE");
            functionname = portfunction.getst("SERVICE_FUNCTION");
            if ((servername == null) || (functionname == null)) {
                log("server name and/or server function undefined.");
                return;
            }

            context = new Context();
            context.items = getFunction(function, sapfunctionname);
            transfer(context, sapfunction);
            context.structures = external.
                    getFunctionStructures(sapfunctionname);
            
            message = new Message(functionname);
            message.add("function", sapfunctionname);
            message.add("parameters", context.result);
            
            extract(context);
            
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
        } finally {
            external.disconnect();
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

