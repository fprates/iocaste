package org.iocaste.office;

import org.hibernate.Session;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("send_message", "sendMessage");
    }
    
    public final void sendMessage(Message message) throws Exception {
        Documents documents;
        OfficeMessage message_ = (OfficeMessage)message.get("message");
        
        if (message_ == null)
            throw new Exception("Message not especified.");
        
        documents = new Documents(this);

        message_.setId(documents.getNextNumber("OFFICEMSGNR"));
        
        Session session = getHibernateSession();
        session.beginTransaction();
        session.save(message_);
        session.getTransaction().commit();
    }
}
