package org.iocaste.external.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.iocaste.external.common.MessageExtractor;
import org.iocaste.protocol.AbstractService;
import org.iocaste.protocol.stream.SocketStream;

public class ExternCallStream extends SocketStream {

    public ExternCallStream(String address, int port) {
        super(address, port);
    }
    
    @Override
    public final Object[] read() throws Exception {
        BufferedReader br;
        InputStream cis;
        Object[] response;
        
        cis = getInputStream();
        br = new BufferedReader(new InputStreamReader(cis));
        response = AbstractService.
                disassembly(new MessageExtractor().execute(br));
        br.close();
        return response;
    }
    
    @Override
    public final void write(Object[] content) throws Exception {
        BufferedWriter bw;
        OutputStream cos;
        
        cos = getOutputStream();
        bw = new BufferedWriter(new OutputStreamWriter(cos));
        bw.write(AbstractService.assembly(content).toString());
        bw.flush();
    }
}