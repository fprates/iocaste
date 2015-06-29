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

public class Main extends AbstractExternalApplication {
	
	@Override
	protected final void config() {
		required("--file", KEY_VALUE);
		option("--language", KEY_VALUE, "pt_BR");
	}
	
	@Override
	protected final void execute(Message message) {
        Transport transport;
        SeekableByteChannel channel;
        Path path;
		String id, filename = message.getString("--file");
		ByteBuffer buffer = ByteBuffer.allocate(64 * 1024);

        transport = new Transport(connector);
        path = Paths.get(filename);
        id = transport.start(path.getFileName().toString());
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
	}
	
	public static final void main(String[] args) {
		new Main().init(args);
	}
}
