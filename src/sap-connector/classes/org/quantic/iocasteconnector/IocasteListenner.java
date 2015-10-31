package org.quantic.iocasteconnector;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.external.common.AbstractExternalFunction;
import org.iocaste.external.common.IocasteConnector;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRuntimeException;

public class IocasteListenner extends AbstractExternalFunction {
    private JCoDestination destination;

    public IocasteListenner(JCoDestination destination) {
        this.destination = destination;
    }
    
    @Override
    public void execute(Socket socket, IocasteConnector connector)
            throws Exception {
        Map<String, Object> values;
        Service service;
        Message message;
        String name;
        JCoFunction sapfunction;
        Context context;
        
        service = new Service(null, socket.getInetAddress().getHostAddress());
        service.setInputStream(socket.getInputStream());
        service.setOutputStream(socket.getOutputStream());
        message = service.getMessage();
        
        try {
            name = message.getId();
            sapfunction = destination.getRepository().getFunction(name);
            if (sapfunction == null)
                throw new IocasteException(new StringBuilder("SAP function ").
                        append(name).
                        append(" not found.").toString());
            
            System.out.print("invoking "+name+"...");
            external.connect();
            context = new Context();
            context.items = FunctionHandler.getFunction(connector, name);
            context.lists.put(
                    "importing", sapfunction.getImportParameterList());
            context.lists.put(
                    "exporting", sapfunction.getExportParameterList());
            context.lists.put(
                    "changing", sapfunction.getChangingParameterList());
            context.lists.put(
                    "tables", sapfunction.getTableParameterList());
            context.structures = external.
                    getFunctionStructures(name);
            context.result = new HashMap<>();
            for (String keylist : new String[] {
                    "importing", "changing", "tables"
            }) {
                values = message.get(keylist);
                if (values == null)
                    continue;
                for (String key : values.keySet())
                    context.result.put(key, values.get(key));
            }
            
            FunctionHandler.prepareToExport(context);
            sapfunction.execute(destination);
            service.messageReturn(message, null);
            System.out.println("ok.");
        } catch (JCoRuntimeException e) {
            e.printStackTrace();
            service.messageException(message, new Exception(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            service.messageException(message, e);
        } finally {
            external.disconnect();
        }
    }
}
