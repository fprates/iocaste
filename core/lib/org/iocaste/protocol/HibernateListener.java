package org.iocaste.protocol;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;

public class HibernateListener implements ServletContextListener {
    private static SessionFactory sessionFactory;

    public static final SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(
     *     javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        sessionFactory.close();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(
     *     javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

}
