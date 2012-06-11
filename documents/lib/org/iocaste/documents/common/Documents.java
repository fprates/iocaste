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
    public static final int TABLE_ALREADY_ASSIGNED = 1;
    Function function;
    
    public Documents(Function function) {
        this.function = function;
        initService(function, SERVERNAME);
    }
    
    /**
     * 
     * @throws Exception
     */
    public final void commit() throws Exception {
        new Iocaste(function).commit();
    }
    
    /**
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public final int createModel(DocumentModel model) throws Exception {
        Message message = new Message();
        
        message.setId("create_model");
        message.add("model", model);
        
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @throws Exception
     */
    public final void createNumberFactory(String name) throws Exception {
        Message message = new Message();
        
        message.setId("create_number_factory");
        message.add("name", name);
        
        call(message);
    }
    
    /**
     * 
     * @param object
     * @return
     * @throws Exception
     */
    public final int delete(ExtendedObject object) throws Exception {
        Message message = new Message();
        
        message.setId("delete");
        message.add("object", object);
        
        return call(message);
    }
    
    /**
     * 
     * @param item
     * @return
     */
    public static final String getComposedName(DocumentModelItem item) {
        return new StringBuilder(item.getDocumentModel().getName()).
                append(".").append(item.getName()).toString();
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public final DataElement getDataElement(String name) throws Exception {
        Message message = new Message();
        
        message.setId("get_data_element");
        message.add("name", name);
        
        return call(message);
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
        
        return call(message);
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
        
        return call(message);
    }
    
    /**
     * 
     * @param modelname
     * @param key
     * @return
     * @throws Exception
     */
    public final ExtendedObject getObject(String modelname, Object key)
            throws Exception {
        Message message = new Message();
        
        message.setId("get_object");
        message.add("modelname", modelname);
        message.add("key", key);
        
        return call(message);
    }
    
    /**
     * 
     * @param model
     * @param key
     * @return
     * @throws Exception
     */
    public final boolean isLocked(String model, String key)
            throws Exception {
        Message message = new Message();
        
        message.setId("is_locked");
        message.add("model", model);
        message.add("key", key);
        
        return call(message);
    }
    
    /**
     * 
     * @param model
     * @param key
     * @return
     * @throws Exception
     */
    public final int lock(String model, String key) throws Exception {
        Message message = new Message();
        
        message.setId("lock");
        message.add("model", model);
        message.add("key", key);
        
        return call(message);
    }
    
    /**
     * 
     * @param object
     * @return
     * @throws Exception
     */
    public final int modify(ExtendedObject object) throws Exception {
        Message message = new Message();
        
        message.setId("modify");
        message.add("object", object);
        
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public final int removeModel(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove_model");
        message.add("model_name", name);
        
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public final int removeNumberFactory(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove_number_factory");
        message.add("name", name);
        
        return call(message);
    }
    
    /**
     * 
     * @param oldname
     * @param newname
     * @return
     * @throws Exception
     */
    public final int renameModel(String oldname, String newname)
            throws Exception {
        Message message = new Message();
        
        message.setId("rename_model");
        message.add("oldname", oldname);
        message.add("newname", newname);
        
        return call(message);
    }
    
    /**
     * 
     * @throws Exception
     */
    public final void rollback() throws Exception {
        new Iocaste(function).rollback();
    }
    
    /**
     * 
     * @param object
     * @return
     * @throws Exception
     */
    public final int save(ExtendedObject object) throws Exception {
        Message message = new Message();
        
        message.setId("save");
        message.add("object", object);
        
        return call(message);
    }
    
    /**
     * 
     * @param query
     * @param criteria
     * @return
     * @throws Exception
     */
    public final ExtendedObject[] select(String query, Object... criteria)
    		throws Exception {
        return selectLimitedTo(query, 0, criteria);
    }
    
    /**
     * 
     * @param query
     * @param rows
     * @param criteria
     * @return
     * @throws Exception
     */
    public final ExtendedObject[] selectLimitedTo(String query, int rows,
            Object... criteria) throws Exception {
        Message message = new Message();
        
        message.setId("select");
        message.add("query", query);
        message.add("criteria", criteria);
        message.add("rows", rows);
        
        return call(message);
        
    }
    
    /**
     * 
     * @param model
     * @param key
     * @return
     * @throws Exception
     */
    public final int unlock(String model, String key) throws Exception {
        Message message = new Message();
        
        message.setId("unlock");
        message.add("model", model);
        message.add("key", key);
        
        return call(message);
    }
    
    /**
     * 
     * @param query
     * @param criteria
     * @return
     * @throws Exception
     */
    public final int update(String query, Object... criteria)
            throws Exception {
        Message message = new Message();
        
        message.setId("update");
        message.add("query", query);
        message.add("criteria", criteria);
        
        return call(message);
    }
    
    /**
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public final int updateModel(DocumentModel model) throws Exception {
        Message message = new Message();
        
        message.setId("update_model");
        message.add("model", model);
        
        return call(message);
    }
    
    /**
     * 
     * @param model
     * @return
     * @throws Exception
     */
    public final int validate(DocumentModel model) throws Exception {
        Message message = new Message();
        
        message.setId("validate_model");
        message.add("model", model);
        
        return call(message);
    }
}