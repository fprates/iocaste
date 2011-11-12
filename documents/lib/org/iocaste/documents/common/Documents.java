/*
    Documents.java, chamadas do módulo de documentos
    Copyright (C) 2011  Francisco de Assis Prates
   
    Este programa é software livre; você pode redistribuí-lo e/ou
    modificá-lo sob os termos da Licença Pública Geral GNU, conforme
    publicada pela Free Software Foundation; tanto a versão 2 da
    Licença como (a seu critério) qualquer versão mais nova.

    Este programa é distribuído na expectativa de ser útil, mas SEM
    QUALQUER GARANTIA; sem mesmo a garantia implícita de
    COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
    PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
    detalhes.
 
    Você deve ter recebido uma cópia da Licença Pública Geral GNU
    junto com este programa; se não, escreva para a Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
    02111-1307, USA.
 */

package org.iocaste.documents.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

/**
 * Chamadas do módulo de documentos
 * @author Francisco Prates
 *
 */
public class Documents extends AbstractServiceInterface {
    private static final String SERVERNAME =
            "/iocaste-documents/services.html";
    private Function function;
    
    public Documents(Function function) {
        this.function = function;
        initService(function, SERVERNAME);
    }
    
    public final int delete(ExtendedObject object) throws Exception {
        int i = 0;
        Iocaste iocaste = new Iocaste(function);
        DocumentModel model = object.getModel();
        Set<DocumentModelKey> keys = model.getKeys();
        Object[] criteria = new Object[keys.size()];
        
        for (DocumentModelKey key : keys)
            criteria[i++] = key.getModelItem();
        
        return iocaste.update(model.getQuery("delete"), criteria);
    }
    
    /**
     * Obtem instância do modelo de documento informado.
     * @param nome do modelo
     * @return modelo de documento
     * @throws InvalidModel
     */
    public final DocumentModel getModel(String name) throws Exception {
        Message message = new Message();
        
        message.setId("get_document_model");
        message.add("name", name);
        
        return (DocumentModel)call(message);
    }
    
    /**
     * Obtem próximo número do range informado.
     * @param nome do range
     * @return número
     * @throws InvalidRange
     */
    public final long getNextNumber(String range) throws Exception {
        Message message = new Message();
        
        message.setId("get_next_number");
        message.add("range", range);
        
        return (Long)call(message);
    }
    
    /**
     * 
     * @param model
     * @param object
     * @throws Exception 
     */
    public final void modify(ExtendedObject object) throws Exception {
    	Object value;
    	DocumentModel model = object.getModel();
        Iocaste iocaste = new Iocaste(function);
    	List<Object> criteria = new ArrayList<Object>();
    	List<Object> uargs = new ArrayList<Object>();
    	List<Object> iargs = new ArrayList<Object>();
    	
        for (DocumentModelItem item : model.getItens()) {
        	value = object.getValue(item);
        	
        	iargs.add(value);
    		if (model.isKey(item))
    			criteria.add(value);
    		else
            	uargs.add(value);
        }
        
        uargs.addAll(criteria);
        
        if (iocaste.update(model.getQuery("update"), uargs.toArray()) == 0)
            iocaste.update(model.getQuery("insert"), iargs.toArray());
    }
    
    /**
     * 
     * @param query
     * @return
     * @throws Exception
     */
    private final QueryInfo reparseQuery(String query) throws Exception {
        String[] select;
        int t;
        String[] parsed = query.split(" ");
        int pass = 0;
        StringBuilder sb = new StringBuilder("select ");
        QueryInfo queryinfo = new QueryInfo();
        
        for (String token : parsed) {
            switch (pass) {
            case 0:
                if (token.equals("select")) {
                    pass = 1;
                    continue;
                }
                
                if (token.equals("from")) {
                    pass = 3;
                    sb.append("* from ");
                    continue;
                }
                
                continue;
            case 1:
                select = token.split(",");
                t = select.length;
                
                for (int i = 0; i < t; i++) {
                    sb.append(select[i]);
                    if (i == (t - 1))
                        continue;
                    sb.append(",");
                }
                
                pass = 2;
                continue;
            case 2:
                if (token.equals("from"))
                    sb.append(" from ");
                
                pass = 3;
                continue;
            case 3:
            	queryinfo.model = getModel(token);
            	if (queryinfo.model == null)
            		throw new Exception("Document model not found.");
            	
                sb.append(queryinfo.model.getTableName());
                continue;
            }
        }
        
        queryinfo.query = sb.toString();
        
        return queryinfo;
    }
    
    /**
     * 
     * @param object
     * @return
     * @throws Exception
     */
    public final int save(ExtendedObject object) throws Exception {
        Object[] criteria;
        Iocaste iocaste = new Iocaste(function);
        DocumentModel model = object.getModel();
        Set<DocumentModelItem> itens = model.getItens();
        int i = itens.size();
        
        criteria = (i > 0)? new Object[i] : null;
        
        i = 0;
        for (DocumentModelItem item : model.getItens())
            criteria[i++] = object.getValue(item);
        
        return iocaste.update(model.getQuery("insert"), criteria);
    }
    
    /**
     * 
     * @param query
     * @param criteria
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final ExtendedObject[] select(String query, Object[] criteria)
    		throws Exception {
        Object value;
        Object[] lines;
        Map<String, Object> line;
        ExtendedObject object;
        ExtendedObject[] objects;
        Iocaste iocaste = new Iocaste(function);
        QueryInfo queryinfo = reparseQuery(query);
        
        if (queryinfo.query == null || queryinfo.model == null)
            return null;
        
        lines = iocaste.select(queryinfo.query, criteria);
        if (lines.length == 0)
            return null;
        
        objects = new ExtendedObject[lines.length];
        
        for (int i = 0; i < lines.length; i++) {
        	line = (Map<String, Object>)lines[i];
        	object = new ExtendedObject(queryinfo.model);
        	
        	for (DocumentModelItem modelitem : queryinfo.model.getItens()) {
        		value = line.get(modelitem.getTableFieldName());
        		object.setValue(modelitem, value);
        	}
        	
        	objects[i] = object;
        }
        
        return objects;
    }
}

class QueryInfo {
	public DocumentModel model;
	public String query;
	
	public QueryInfo() {
		model = null;
		query = null;
	}
}