package org.iocaste.transport.robot;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.iocaste.external.common.AbstractExternalApplication;
import org.iocaste.protocol.Message;
import org.iocaste.transport.common.Transport;
import org.iocaste.workbench.common.Workbench;

public class Main extends AbstractExternalApplication {
	
	@Override
	protected final void config() {
		required("--file", KEY_VALUE);
		option("--language", KEY_VALUE, "pt_BR");
	}
	
	@Override
	protected final void execute(Message message) {
	    Workbench workbench;
        Transport transport;
        SeekableByteChannel channel;
        Path path;
		String id, name;
		String filename = message.getst("--file");
		ByteBuffer buffer = ByteBuffer.allocate(64 * 1024);

        transport = new Transport(connector);
        path = Paths.get(filename);
        name = path.getFileName().toString();
        id = transport.start(name);
        try {
            channel = Files.newByteChannel(path);
            while (channel.read(buffer) > 0) {
                buffer.rewind();
                transport.send(id, buffer.array());
                buffer.flip();
            }
            channel.close();
        } catch (IOException e) {
            transport.cancel(id);
            System.err.println("I/O error reading archive.");
            System.exit(1);
        }
        
        transport.finish(id);
        
        workbench = new Workbench(connector);
        workbench.fetch(name);
        workbench.build(name);
	}
	
	public static final void main(String[] args) throws Exception {
		new Main().init(args);
	}
}
