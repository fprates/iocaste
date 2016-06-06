package org.iocaste.external.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.iocaste.external.common.MessageExtractor;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.stream.SocketStream;

public class ExternCallStream extends SocketStream {

    public ExternCallStream(String address, int port) {
        super(address, port);
    }
    
    @Override
    public final Message read() throws Exception {
        BufferedReader br;
        InputStream cis;
        Message response;
        
        cis = getInputStream();
        br = new BufferedReader(new InputStreamReader(cis));
        response = new MessageExtractor().execute(br);
        br.close();
        return response;
    }
    
    @Override
    public final void write(Message message) throws Exception {
        BufferedWriter bw;
        OutputStream cos;
        
        cos = getOutputStream();
        bw = new BufferedWriter(new OutputStreamWriter(cos));
        bw.write(message.toString());
        bw.flush();
    }
}