package org.quantic.iocasteconnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.external.common.AbstractExternalFunction;
import org.iocaste.external.common.IocasteConnector;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRuntimeException;

public class IocasteListenner extends AbstractExternalFunction {
	private Command stream;

    public IocasteListenner(Command stream) {
    	this.stream = stream;
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
        
        service = new StandardService(
                null, socket.getInetAddress().getHostAddress());
        service.setInputStream(socket.getInputStream());
        service.setOutputStream(socket.getOutputStream());
        message = service.getMessage();
        
        try {
            if (!external.connect())
                throw new IocasteException("iocaste connection failed.");

            preconn = stream.portconfig.getHeader().getst("PRE_CONNECTION");
            if (preconn != null) {
                System.out.print("pre-connecting... ");
                err = rtexecute(preconn);
                if (err == 0)
                    System.out.println("ok");
                else
                    System.err.println(new StringBuilder("returned ").
                            append(err).toString());
                
                if (stream.provider != null)
                	AbstractSAPFunctionHandler.unregister(stream);
                AbstractSAPFunctionHandler.register(stream);
            }
            
            name = message.getId();
            System.out.print("invoking "+name+"...");
            
            sapfunction = stream.destination.getRepository().getFunction(name);
            if (sapfunction == null)
                throw new IocasteException(new StringBuilder("SAP function ").
                        append(name).
                        append(" not found.").toString());
            
            context = new Context();
            context.items = AbstractSAPFunctionHandler.
                    getFunction(connector, name);
            AbstractSAPFunctionHandler.transfer(context, sapfunction);
            context.structures = external.getFunctionStructures(name);
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
            
            AbstractSAPFunctionHandler.prepareToExport(context);
            sapfunction.execute(stream.destination);
            AbstractSAPFunctionHandler.transfer(context, sapfunction);
            AbstractSAPFunctionHandler.extract(context);
            service.messageReturn(message, context.result);
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
            postconn = stream.portconfig.getHeader().getst("POST_CONNECTION");
            if (postconn != null) {
            	AbstractSAPFunctionHandler.unregister(stream);
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
