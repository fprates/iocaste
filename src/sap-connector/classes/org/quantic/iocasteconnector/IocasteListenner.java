package org.quantic.iocasteconnector;

import java.net.Socket;

import org.iocaste.external.common.AbstractExternalFunction;
import org.iocaste.external.common.IocasteConnector;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;

public class IocasteListenner extends AbstractExternalFunction {
    public JCoDestination destination;

    @Override
    public void execute(Socket socket, IocasteConnector connector)
            throws Exception {
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
            
            FunctionHandler.prepareToExport(context);
            sapfunction.execute(destination);
        } catch (Exception e) {
            e.printStackTrace();
            service.messageException(message, e);
        } finally {
            external.disconnect();
        }
    }
}
