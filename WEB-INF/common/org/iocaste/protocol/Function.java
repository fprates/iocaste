package org.iocaste.protocol;

import java.util.List;

import org.hibernate.SessionFactory;

public interface Function {
    public abstract List<String> getMethods();

    public abstract Object run(Message message) throws Exception;
    
    public abstract void setSessionFactory(SessionFactory sessionFactory);
}
