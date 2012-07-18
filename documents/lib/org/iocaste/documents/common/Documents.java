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
     */
    public final void commit() {
        new Iocaste(function).commit();
    }
    
    /**
     * Registra modelo complexo.
     * 
     * @param cmodel modelo complexo
     * @return 1, se o modelo foi registrado com sucesso.
     */
    public final int create(ComplexModel cmodel) {
        Message message = new Message();
        
        message.setId("create_complex_model");
        message.add("cmodel", cmodel);
        return call(message);
    }
    
    /**
     * Registra modelo de documento.
     * 
     * Cria a tabela associada ao modelo.
     * 
     * @param model dados do modelo.
     * @return 1, se modelo foi registrado com sucesso.
     */
    public final int createModel(DocumentModel model) {
        Message message = new Message();
        
        message.setId("create_model");
        message.add("model", model);
        return call(message);
    }
    
    /**
     * Registra objeto de numeração.
     * @param name nome do objeto.
     */
    public final void createNumberFactory(String name) {
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
     */
    public final int delete(ExtendedObject object) {
        Message message = new Message();
        
        message.setId("delete");
        message.add("object", object);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ComplexModel getComplexModel(String name) {
        Message message = new Message();
        
        message.setId("get_complex_model");
        message.add("name", name);
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
     */
    public final DataElement getDataElement(String name) {
        Message message = new Message();
        
        message.setId("get_data_element");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Obtem instância do modelo de documento informado.
     * @param nome do modelo
     * @return modelo de documento
     */
    public final DocumentModel getModel(String name) {
        Message message = new Message();
        
        message.setId("get_document_model");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Obtem próximo número do range informado.
     * @param nome do range
     * @return número
     */
    public final long getNextNumber(String range) {
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
     */
    public final ExtendedObject getObject(String modelname, Object key) {
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
     */
    public final boolean isLocked(String model, String key) {
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
     */
    public final int lock(String model, String key) {
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
     */
    public final int modify(ExtendedObject object) {
        Message message = new Message();
        
        message.setId("modify");
        message.add("object", object);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final int removeComplexModel(String name) {
        Message message = new Message();
        
        message.setId("remove_complex_model");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Remove modelo de documento especificado.
     * @param name nome do modelo.
     * @return 1, se o modelo foi removido com sucesso.
     */
    public final int removeModel(String name) {
        Message message = new Message();
        
        message.setId("remove_model");
        message.add("model_name", name);
        return call(message);
    }
    
    /**
     * Remove objeto de numeração especificado.
     * @param name nome do objeto.
     * @return 1, se o objeto foi removido com sucesso.
     */
    public final int removeNumberFactory(String name) {
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
     */
    public final int renameModel(String oldname, String newname) {
        Message message = new Message();
        
        message.setId("rename_model");
        message.add("oldname", oldname);
        message.add("newname", newname);
        return call(message);
    }
    
    /**
     * Efetua rollback no banco.
     */
    public final void rollback() {
        new Iocaste(function).rollback();
    }
    
    /**
     * 
     * @param document
     * @return retorna código do documento, ou 
     * 0, se erro na criação do documento;
     */
    public final long save(ComplexDocument document) {
        Message message = new Message();
        
        message.setId("save_complex_document");
        message.add("document", document);
        return call(message);
    }
    
    /**
     * Insere entrada em tabela, especificado pelo objeto.
     * @param object objeto a ser inserido
     * @return 1, se o objeto foi inserido com sucesso.
     */
    public final int save(ExtendedObject object) {
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
     */
    public final ExtendedObject[] select(String query, Object... criteria) {
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
     */
    public final ExtendedObject[] selectLimitedTo(String query, int rows,
            Object... criteria) {
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
     */
    public final int unlock(String model, String key) {
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
     */
    public final int update(String query, Object... criteria) {
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
     */
    public final int updateModel(DocumentModel model) {
        Message message = new Message();
        
        message.setId("update_model");
        message.add("model", model);
        return call(message);
    }
    
    /**
     * - not ready, do not use -
     * @param model
     * @return
     */
    public final int validate(DocumentModel model) {
        Message message = new Message();
        
        message.setId("validate_model");
        message.add("model", model);
        return call(message);
    }
}