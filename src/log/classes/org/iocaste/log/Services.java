package org.iocaste.log;

import java.util.Date;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("commit", new LogCommit());
    }
    
}

class LogCommit extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String fd;
        String[] messages = message.get("messages");
        Iocaste iocaste = new Iocaste(getFunction());
        StringBuilder sb = new StringBuilder(new Date().toString()).
                append(".txt");
        String[] path = {"log", sb.toString()};
        
        if (!iocaste.exists("log"))
            iocaste.mkdir("log");
        fd = iocaste.file(
                iocaste.exists(path)? Iocaste.CREATE : Iocaste.WRITE, path);
        sb.setLength(0);
        for (String text : messages)
            sb.append(text).append("\n");
        iocaste.write(fd, sb.toString().getBytes());
        iocaste.close(fd);
        return null;
    }
    
}
