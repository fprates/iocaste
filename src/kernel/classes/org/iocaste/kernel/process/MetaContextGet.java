package org.iocaste.kernel.process;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.iocaste.kernel.files.FileRead;
import org.iocaste.kernel.files.FileServices;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class MetaContextGet extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        byte[] stream;
        RandomAccessFile raf;
        FileChannel channel;
        String path;
        String file = message.getst("file");
        String pkgname = message.getst("package");
        ProcessServices services = getFunction();
        FileRead fileread = services.files.get("read");
        
        path = FileServices.composeFileName(System.getProperty("catalina.home"),
                "webapps", pkgname, 
                "META-INF", file);
        
        raf = new RandomAccessFile(path, "r");
        channel = raf.getChannel();
        stream = fileread.run(channel);
        raf.close();
        return stream;
    }
    
}