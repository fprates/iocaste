package org.quantic.iocasteconnector;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.external.common.External;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.server.JCoServerContext;

public class FunctionHandler extends AbstractSAPFunctionHandler {
    private Function function;
    private ExtendedObject portfunction;
    
    public FunctionHandler(Function function, External external,
            ExtendedObject portfunction) {
        super(external, CONNECT);
        this.function = function;
        this.portfunction = portfunction;
    }
    
    @Override
    protected void run(JCoServerContext sapcontext, JCoFunction sapfunction)
            throws Exception {
        Context context;
        String servername, functionname, sapfunctionname;
        Message message;
        GenericService service;

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
    }
}

