package org.iocaste.transport;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.transport.common.Order;

public class Service extends AbstractFunction {

    public Service() {
        export("save", "save");
    }
    
    public final void save(Message message) {
        Order order = (Order)message.get("order");
//      String transportdir = getRealPath("../../../transport");
//      File file = new File(transportdir+"/ot0001.txt");
//      FileWriter fwriter = new FileWriter(file);
//      BufferedWriter bwriter = new BufferedWriter(fwriter);
//      
//      bwriter.write("IOCST_OT");
//      bwriter.newLine();
//      bwriter.flush();
//      bwriter.close();
    }
}
