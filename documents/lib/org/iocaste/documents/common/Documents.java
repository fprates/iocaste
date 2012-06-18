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
 * Serviços do módulo de documentos
 * 
 * Acessa funções de gerenciamento de modelos, consultas,
 * bloqueios e objetos de numeração.
 * 
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
     * Efetua commit.
     * @throws Exception
     */
    public final void commit() throws Exception {
        new Iocaste(function).commit();
    }
    
    /**
     * Registra modelo de documento.
     * 
     * Cria a tabela associada ao modelo.
     * 
     * @param model dados do modelo.
     * @return 1, se modelo foi registrado com sucesso.
     * @throws Exception
     */
    public final int createModel(DocumentModel model) throws Exception {
        Message message = new Message();
        
        message.setId("create_model");
        message.add("model", model);
        
        return call(message);
    }
    
    /**
     * Registra objeto de numeração.
     * @param name nome do objeto.
     * @throws Exception
     */
    public final void createNumberFactory(String name) throws Exception {
        Message message = new Message();
        
        message.setId("create_number_factory");
        message.add("name", name);
        
        call(message);
    }
    
    /**
     * Remove entrada da tabela.
     * 
     * A entrada é especificada por objeto extendido.
     * 
     * @param object objeto extendido
     * @return 1, para remoção bem sucedida.
     * @throws Exception
     */
    public final int delete(ExtendedObject object) throws Exception {
        Message message = new Message();
        
        message.setId("delete");
        message.add("object", object);
        
        return call(message);
    }
    
    /**
     * Retorna nome composto.
     * @param item item de modelo
     * @return nome composto do item.
     */
    public static final String getComposedName(DocumentModelItem item) {
        return new StringBuilder(item.getDocumentModel().getName()).
                append(".").append(item.getName()).toString();
    }
    
    /**
     * Retorna elemento de dados especificado.
     * @param name nome
     * @return elemento de dados, null se não encontrado
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
     * Retorna entrada de dados especificada por modelo e chave.
     * @param modelname nome do modelo
     * @param key identificador (chave)
     * @return objeto encontrado ou null, se não encontrado.
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
     * Indica se entrada em tabela está bloqueada.
     * 
     * Se o registro está bloqueado pela própria sessão,
     * indica como desbloqueado.
     * 
     * @param model modelo
     * @param key chave
     * @return true, se entrada está bloqueada
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
     * Bloqueia entrada de tabela, especificado por modelo e chave.
     * 
     * Não impede que funções atualizem as entradas especificadas,
     * apenas sinaliza que o registro está sendo utilizado por outra
     * aplicação.
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
     * Atualiza entrada especificada pelo objeto extendido.
     * @param object objeto a ser atualizado.
     * @return 1, se a entrada foi atualizada com sucesso.
     * @throws Exception
     */
    public final int modify(ExtendedObject object) throws Exception {
        Message message = new Message();
        
        message.setId("modify");
        message.add("object", object);
        
        return call(message);
    }
    
    /**
     * Remove modelo de documento especificado.
     * @param name nome do modelo.
     * @return 1, se o modelo foi removido com sucesso.
     * @throws Exception
     */
    public final int removeModel(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove_model");
        message.add("model_name", name);
        
        return call(message);
    }
    
    /**
     * Remove objeto de numeração especificado.
     * @param name nome do objeto.
     * @return 1, se o objeto foi removido com sucesso.
     * @throws Exception
     */
    public final int removeNumberFactory(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove_number_factory");
        message.add("name", name);
        
        return call(message);
    }
    
    /**
     * Renomeia modelo especificado.
     * @param oldname nome do modelo.
     * @param newname novo nome do modelo.
     * @return 1, se o modelo foi renomeado com sucesso.
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
     * Efetua rollback no banco.
     * @throws Exception
     */
    public final void rollback() throws Exception {
        new Iocaste(function).rollback();
    }
    
    /**
     * Insere entrada em tabela, especificado pelo objeto.
     * @param object objeto a ser inserido
     * @return 1, se o objeto foi inserido com sucesso.
     * @throws Exception
     */
    public final int save(ExtendedObject object) throws Exception {
        Message message = new Message();
        
        message.setId("save");
        message.add("object", object);
        
        return call(message);
    }
    
    /**
     * Seleciona entradas a partir de query e critérios.
     * 
     * Devem ser especificados modelo e (opcionalmente) itens de modelo.
     * 
     * @param query Query SQL
     * @param criteria critérios de seleção.
     * @return entradas encontradas.
     * @throws Exception
     */
    public final ExtendedObject[] select(String query, Object... criteria)
    		throws Exception {
        return selectLimitedTo(query, 0, criteria);
    }
    
    /**
     * Seleciona entradas a partir de query e critérios, até um limite
     * especificado.
     * 
     * Devem ser especificadoss modelo e (opcionalmente) itens de modelo.
     * 
     * @param query Query SQL
     * @param rows quantidade de acertos
     * @param criteria critérios de seleção
     * @return entradas encontradas.
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
     * Desbloqueia entrada de tabela, especificado por modelo e chave.
     * 
     * Não efetua desbloqueio de entradas bloqueadas por outras sessões.
     * 
     * @param model modelo
     * @param key identificador da entrada
     * @return 1, se foi desbloqueado com sucesso.
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
     * Atualiza uma entrada especificada por declaração SQL e critérios.
     * @param query declaração SQL
     * @param criteria critérios
     * @return quantidade de registros atualizados com sucesso.
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
     * Atualiza modelo de documento.
     * @param model dados do modelo de documento.
     * @return 1, se o modelo foi atualizado.
     * @throws Exception
     */
    public final int updateModel(DocumentModel model) throws Exception {
        Message message = new Message();
        
        message.setId("update_model");
        message.add("model", model);
        
        return call(message);
    }
    
    /**
     * - not ready, do not use -
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