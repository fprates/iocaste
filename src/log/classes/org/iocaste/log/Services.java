package org.iocaste.log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("commit", "commit");
    }
    
    public final void commit(Message message) throws Exception {
        String filename;
        File file;
        BufferedOutputStream bos;
        List<String> messages = message.get("messages");
        
        filename = new StringBuilder(System.getProperty("user.home")).
                append(File.separator).
                append(".iocaste").
                append(File.separator).
                append("log").toString();
        file = new File(filename);
        if (!file.exists())
            file.mkdirs();
        
        filename = new StringBuilder(filename).
                append(File.separator).
                append(new Date().toString()).
                append(".txt").toString();
        file = new File(filename);
        if (!file.exists())
            file.createNewFile();
        
        bos = new BufferedOutputStream(new FileOutputStream(file));
        for (String text : messages)
            bos.write(text.getBytes());
        
        bos.flush();
        bos.close();
    }
}
