package org.iocaste.documents.range;

import org.hibernate.Session;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class NumericRangeFunction extends AbstractFunction {

    public NumericRangeFunction() {
        export("get_next_number", "getNextNumber");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final long getNextNumber(Message message) throws Exception {
        Session session;
        NumericRange range;
        String ident = message.getString("range");
        
        if (ident == null)
            throw new Exception("Numeric range not especified.");
        
        range = new NumericRange();
        
        load(NumericRange.class, range);
        range.setCurrent(range.getCurrent()+1);

        session = getHibernateSession();
        session.beginTransaction();
        session.update(range);
        session.getTransaction().commit();
        
        return range.getCurrent();
    }
}
