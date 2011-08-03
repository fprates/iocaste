package org.iocaste.protocol;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractFunction implements Function {
    private SessionFactory sessionFactory;
    private ServletContext context;
    private Map<String, String> queries;
    private Map<String, String> exports;
    private String servername;
    private String sessionid;
    
    public AbstractFunction() {
        queries = new HashMap<String, String>();
        exports = new HashMap<String, String>();
    }
    
    /**
     * 
     * @param queryname
     * @param sqlquery
     */
    protected final void addQuery(String queryname, String sqlquery) {
        queries.put(queryname, sqlquery);
    }
    
    /**
     * 
     * @param name
     * @param method
     */
    protected final void export(String name, String method) {
        exports.put(name, method);
    }
    
    /**
     * 
     * @return
     */
    protected final Session getHibernateSession() {
        return sessionFactory.getCurrentSession();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#getMethods()
     */
    @Override
    public final Set<String> getMethods() {
        return exports.keySet();
    }
    
    /**
     * 
     * @param path
     * @return
     */
    public final InputStream getResourceAsStream(String path) {
        return context.getResourceAsStream(path);
    }
    
    /**
     * 
     * @param class_
     * @param object
     * @return
     */
    protected final Object load(Class<?> class_, Serializable object) {
        Object result;
        
        Session session = sessionFactory.getCurrentSession();
        
        session.beginTransaction();
        result = session.get(class_, object);
        session.getTransaction().commit();
        
        return result;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#run(org.iocaste.protocol.Message)
     */
    @Override
    public final Object run(Message message) throws Exception {
        Method method;
        String id = message.getId();
        String methodname = exports.get(id);
        
        if (methodname == null)
            throw new Exception("Method \""+id+"\" not implemented");

        setSessionid(message.getSessionid());
        
        method = getClass().getMethod(methodname, Message.class);
        
        return method.invoke(this, message);
    }
    
    /**
     * 
     * @param queryid
     * @param criteria
     * @return
     */
    protected final List<?> select(String queryid, Object[] criteria) {
        Query query;
        List<?> results;
        int id = 0;        
        Session session = sessionFactory.getCurrentSession();
        
        session.beginTransaction();
        query = session.createQuery(queries.get(queryid));
        
        if (criteria != null)
            for (Object object : criteria)
                query.setParameter(id++, object);
        
        results = query.list();
        session.getTransaction().commit();
        
        return results;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#serviceInstance(java.lang.String)
     */
    @Override
    public final Service serviceInstance(String path) {
        String url = new StringBuffer(servername).append(path).toString();
        
        return new Service(sessionid, url);
    }
    
    /**
     * 
     * @param queries
     */
    public final void setQueries(Map<String, String> queries) {
        this.queries = queries;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setServerName(java.lang.String)
     */
    @Override
    public final void setServerName(String servername) {
        this.servername = servername;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setServletContext(
     *     javax.servlet.ServletContext)
     */
    @Override
    public final void setServletContext(ServletContext context) {
        this.context = context;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.protocol.Function#setSessionFactory(org.hibernate.SessionFactory)
     */
    @Override
    public final void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public final void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

}
