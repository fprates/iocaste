package org.iocaste.protocol;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionfactory;
    
    static {
        try {
            sessionfactory = new Configuration().configure().
                    buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static final SessionFactory getSessionFactory() {
        return sessionfactory;
    }
}
