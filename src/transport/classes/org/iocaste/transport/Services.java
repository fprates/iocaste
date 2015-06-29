package org.iocaste.transport;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;

public class Services extends AbstractFunction {
    public Map<String, TransportEntry> transfers;
    
    public Services() {
        export("cancel", new CancelTransport());
        export("finish", new FinishTransport());
        export("send", new SendTransport());
        export("start", new StartTransport());
        transfers = new HashMap<>();
    }
    
    public final void instance(String filename) throws Exception {
        TransportEntry entry = new TransportEntry();
        Iocaste iocaste = new Iocaste(this);
        
        iocaste.mkdir("transport");
        entry.kernelid = iocaste.file(Iocaste.CREATE, "transport", filename);
        entry.filename = filename;
        transfers.put(filename, entry);
    }
}
