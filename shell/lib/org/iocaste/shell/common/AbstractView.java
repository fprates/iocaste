package org.iocaste.shell.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class AbstractView extends AbstractFunction {

    public AbstractView() {
        export("get_view_data", "getViewData");
    }
    
    public final ViewData getViewData(Message message) throws Exception {
        String line;
        InputStream is;
        BufferedReader reader;
        ViewData vdata;
        String page = message.getString("page");
        
        if (page == null)
            throw new Exception("Page not especified.");
        
        is = getResourceAsStream(page);
        reader = new BufferedReader(new InputStreamReader(is));
        vdata = new ViewData();
        
        while ((line = reader.readLine()) != null)
            vdata.add(line);
        
        reader.close();
        is.close();
        
        return vdata;
    }
}
