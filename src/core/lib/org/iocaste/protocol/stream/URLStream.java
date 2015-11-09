package org.iocaste.protocol.stream;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLStream extends AbstractServiceStream {
    private URLConnection urlcon;
    private OutputStream os;
    private String urlname;

    public URLStream(String urlname) {
        this.urlname = urlname;
    }
    public final void close() throws Exception {
        os.close();
    }
    
    @Override
    public InputStream getInputStream() throws Exception {
        return urlcon.getInputStream();
    }
    
    @Override
    public OutputStream getOutputStream() throws Exception {
        return (os = urlcon.getOutputStream());
    }
    @Override
    public void open() throws Exception {
        URL url;
        
        url = new URL(urlname);
        urlcon = url.openConnection();
        urlcon.setDoInput(true);
        urlcon.setDoOutput(true);
    }
}
