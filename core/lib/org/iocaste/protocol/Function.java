package org.iocaste.protocol;

import java.util.Set;

import javax.servlet.ServletContext;

import org.hibernate.SessionFactory;

public interface Function {
    public abstract Set<String> getMethods();

    public abstract Object run(Message message) throws Exception;
    
    public abstract void setSessionFactory(SessionFactory sessionFactory);
    
    public abstract void setServletContext(ServletContext context);
}
