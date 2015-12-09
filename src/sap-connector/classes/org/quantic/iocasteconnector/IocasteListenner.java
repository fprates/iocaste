package org.quantic.iocasteconnector;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
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
    private ComplexDocument portconfig;

    public IocasteListenner(
            JCoDestination destination, ComplexDocument portconfig) {
        this.destination = destination;
        this.portconfig = portconfig;
    }
    
    @Override
    public void execute(Socket socket, IocasteConnector connector)
            throws Exception {
        Map<String, Object> values;
        Service service;
        Message message;
        String name, preconn, postconn;
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
            preconn = portconfig.getHeader().getst("PRE_CONNECTION");
            if (preconn != null)
                rtexecute(preconn);
            sapfunction.execute(destination);
            service.messageReturn(message, null);
            postconn = portconfig.getHeader().getst("POST_CONNECTION");
            if (postconn != null)
                rtexecute(postconn);
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
    
    private void rtexecute(String command) throws Exception {
        Runtime.getRuntime().exec(command);
    }
}
