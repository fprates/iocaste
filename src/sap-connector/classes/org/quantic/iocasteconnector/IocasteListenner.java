package org.quantic.iocasteconnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.external.common.AbstractExternalFunction;
import org.iocaste.external.common.IocasteConnector;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
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
        int err;
        
        service = new Service(null, socket.getInetAddress().getHostAddress());
        service.setInputStream(socket.getInputStream());
        service.setOutputStream(socket.getOutputStream());
        message = service.getMessage();
        
        try {
            if (!external.connect())
                throw new IocasteException("iocaste connection failed.");
            
            preconn = portconfig.getHeader().getst("PRE_CONNECTION");
            if (preconn != null) {
                System.out.print("pre-connecting... ");
                err = rtexecute(preconn);
                if (err == 0)
                    System.out.println("ok");
                else
                    System.err.println(new StringBuilder("returned ").
                            append(err).toString());
            }
            
            name = message.getId();
            System.out.print("invoking "+name+"...");
            sapfunction = destination.getRepository().getFunction(name);
            if (sapfunction == null)
                throw new IocasteException(new StringBuilder("SAP function ").
                        append(name).
                        append(" not found.").toString());
            
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
            new Iocaste(connector).commit();
            System.out.println("ok.");
        } catch (JCoException e) {
            new Iocaste(connector).rollback();
            e.printStackTrace();
            service.messageException(message, new Exception(e.getMessage()));
        } catch (JCoRuntimeException e) {
            new Iocaste(connector).rollback();
            e.printStackTrace();
            service.messageException(message, new Exception(e.getMessage()));
        } catch (Exception e) {
            new Iocaste(connector).rollback();
            e.printStackTrace();
            service.messageException(message, e);
        } finally {
            postconn = portconfig.getHeader().getst("POST_CONNECTION");
            if (postconn != null) {
                System.out.print("pos-connecting... ");
                err = rtexecute(postconn);
                if (err == 0)
                    System.out.println("ok");
                else
                    System.err.println(new StringBuilder("returned ").
                            append(err).toString());
            }
            external.disconnect();
        }
    }
    
    private int rtexecute(String command) throws Exception {
        BufferedReader inputreader, errorreader;
        String line;
        Process process;
        
        process = Runtime.getRuntime().exec(command);
        inputreader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        errorreader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));
        while ((line = inputreader.readLine()) != null)
            System.out.println(line);
        inputreader.close();
        while ((line = errorreader.readLine()) != null)
            System.err.println(line);
        errorreader.close();
        process.waitFor();
        return process.exitValue();
    }
}
