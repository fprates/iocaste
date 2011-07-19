package org.iocaste.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.iocaste.protocol.Message;

import org.iocaste.shell.common.AbstractView;
import org.iocaste.shell.common.ViewData;

public class LoginView extends AbstractView {
    
    public final ViewData getViewData(Message message) throws IOException {
        String line;
        InputStream is = getResourceAsStream(message.getString("page"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ViewData vdata = new ViewData();
        
        while ((line = reader.readLine()) != null)
            vdata.add(line);
        
        reader.close();
        is.close();
        
        return vdata;
    }
}
