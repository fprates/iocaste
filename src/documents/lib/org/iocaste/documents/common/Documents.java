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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    public static final String SERVERNAME = "/iocaste-kernel/service.html";
    public static final int TABLE_ALREADY_ASSIGNED = 1;
    private Function function;
    
    public Documents(Function function) {
        this.function = function;
        initService(function, SERVERNAME);
    }
    
    public static final void clear(ExtendedObject object, String item) {
        DocumentModel model = object.getModel();
        
        switch (model.getModelItem(item).getDataElement().getType()) {
        case DataType.BOOLEAN:
            object.set(item, false);
            break;
        case DataType.CHAR:
        case DataType.DATE:
        case DataType.TIME:
            object.set(item, null);
            break;
        default:
            object.set(item, 0);
            break;
        }
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
        Message message = new Message("create_complex_model");
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
        Message message = new Message("create_model");
        message.add("model", model);
        return call(message);
    }
    
    /**
     * Registra objeto de numeração.
     * @param name nome do objeto.
     */
    public final void createNumberFactory(String name) {
        createNumberFactory(name, null);
    }
    
    /**
     * 
     * @param name
     * @param series
     */
    public final void createNumberFactory(String name, Map<String, Long> series)
    {
        Message message = new Message("create_number_factory");
        message.add("name", name);
        message.add("series", series);
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
        Message message = new Message("delete_document");
        message.add("object", object);
        return call(message);
    }
    
    /**
     * 
     * @param cmodelname
     * @param key
     */
    public final void deleteComplexDocument(String cmodelname, Object key) {
        Message message = new Message("delete_complex_document");
        message.add("cmodel_name", cmodelname);
        message.add("id", key);
        call(message);
    }
    
    /**
     * 
     * @param name
     * @param id
     * @return
     */
    public final ComplexDocument getComplexDocument(String name, Object id) {
        Message message = new Message("get_complex_document");
        message.add("name", name);
        message.add("id", id);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ComplexModel getComplexModel(String name) {
        Message message = new Message("get_complex_model");
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
        Message message = new Message("get_data_element");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Obtem instância do modelo de documento informado.
     * @param nome do modelo
     * @return modelo de documento
     */
    public final DocumentModel getModel(String name) {
        Message message = new Message("get_document_model");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Obtem próximo número do range informado.
     * @param nome do range
     * @return número
     */
    public final long getNextNumber(String range) {
        return getNextNumber(range, null);
    }
    
    /**
     * 
     * @param range
     * @param serie
     * @return
     */
    public final long getNextNumber(String range, String serie) {
        Message message = new Message("get_next_number");
        message.add("range", range);
        message.add("serie", serie);
        return call(message);
    }
    
    /**
     * Retorna entrada de dados especificada por modelo e chave.
     * @param modelname nome do modelo
     * @param key identificador (chave)
     * @return objeto encontrado ou null, se não encontrado.
     */
    public final ExtendedObject getObject(String modelname, Object key) {
        return getObject(modelname, null, key);
    }
    
    /**
     * Retorna entrada de dados especificada por modelo e chave.
     * @param modelname nome do modelo
     * @param ns namespace
     * @param key identificador (chave)
     * @return objeto encontrado ou null, se não encontrado.
     */
    public final ExtendedObject getObject(
            String modelname, Object ns, Object key) {
        Message message = new Message("get_object");
        message.add("modelname", modelname);
        message.add("ns", ns);
        message.add("key", key);
        return call(message);
    }
    
    /**
     * Verifica se um campo do objeto extendido é inicial.
     * @param object Objeto extendido
     * @param name Nome do campo
     * @return true, se o valor do campo do objeto é inicial.
     */
    public static final boolean isInitial(ExtendedObject object, String name) {
        Object value = object.get(name);
        DataElement element = object.getModel().getModelItem(name).
                getDataElement();
        return isInitial(element, value);
    }
    
    /**
     * Verifica que a valor String é inicial
     * @param value String
     * @return true, se a String é nula ou tiver comprimento 0.
     */
    public static final boolean isInitial(String value) {
        if (value == null)
            return true;
        else
            return (value.trim().length() == 0)? true : false;
    }
    
    /**
     * Verifica se um valor do tipo do elemento de dados é inicial.
     * @param element Elemento de dados
     * @param value Valor
     * @return true, se o valor é considerado inicial.
     */
    public static final boolean isInitial(DataElement element, Object value) {
        if (element == null)
            return isInitial((String)value);
        
        switch (element.getType()) {
        case DataType.BOOLEAN:
            return (boolean)value;
            
        case DataType.NUMC:
            if (value instanceof BigDecimal)
                return (((BigDecimal)value).longValue() == 0l);
            else
                return (((Number)value).longValue() == 0l);
            
        case DataType.DEC:
            return (((Number)value).doubleValue() == 0);

        default:
            return (value == null)? true : isInitial(value.toString());
        }
    }
    
    /**
     * Verifica se objeto extendido é inicial
     * @param object objeto extendido
     * @return true, se todos os campos do objeto forem iniciais.
     */
    public static final boolean isInitial(ExtendedObject object) {
        return isInitialIgnoring(object, null);
    }
    
    public static final boolean isInitialIgnoring(
            ExtendedObject object, Set<String> ignore) {
        DocumentModel model = object.getModel();
        
        for (DocumentModelItem item : model.getItens())
            if (!ignore.contains(item.getName()) &&
                    !isInitial(item.getDataElement(), object.get(item)))
                return false;
        
        return true;
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
        Message message = new Message("is_locked");
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
        Message message = new Message("lock");
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
        Message message = new Message("modify");
        message.add("object", object);
        return call(message);
    }
    
    /**
     * 
     * @param to
     * @param from
     */
    public static final void move(ExtendedObject to, ExtendedObject from) {
        String name;
        DocumentModel modelfrom = from.getModel();
        DocumentModel modelto = to.getModel();
        
        for (DocumentModelItem item : modelfrom.getItens()) {
            name = item.getName();
            if (!modelto.contains(name))
                continue;
            
            to.set(name, from.get(item));
        }
    }
    
    /**
     * 
     * @param to
     * @param from
     */
    public static final void moveOnly(
            ExtendedObject to, ExtendedObject from, String... fields) {
        String name;
        DocumentModel modelfrom = from.getModel();
        DocumentModel modelto = to.getModel();
        Set<String> fieldset = new HashSet<>();
        
        for (String field : fields)
            fieldset.add(field);
        
        for (DocumentModelItem item : modelfrom.getItens()) {
            name = item.getName();
            if (!modelto.contains(name) || !fieldset.contains(name))
                continue;
            
            to.set(name, from.get(item));
        }
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final int removeComplexModel(String name) {
        Message message = new Message("remove_complex_model");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Remove modelo de documento especificado.
     * @param name nome do modelo.
     * @return 1, se o modelo foi removido com sucesso.
     */
    public final int removeModel(String name) {
        Message message = new Message("remove_model");
        message.add("model_name", name);
        return call(message);
    }
    
    /**
     * Remove objeto de numeração especificado.
     * @param name nome do objeto.
     * @return 1, se o objeto foi removido com sucesso.
     */
    public final int removeNumberFactory(String name) {
        Message message = new Message("remove_number_factory");
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
        Message message = new Message("rename_model");
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
    public final void save(ComplexDocument document) {
        Message message = new Message("save_complex_document");
        message.add("document", document);
        call(message);
    }
    
    /**
     * Insere entrada em tabela, especificado pelo objeto.
     * @param object objeto a ser inserido
     * @return 1, se o objeto foi inserido com sucesso.
     */
    public final int save(ExtendedObject object) {
        Message message = new Message("save_document");
        message.add("object", object);
        return call(message);
    }
    
    /**
     * Seleciona entradas de tabela a partir de query.
     * 
     * @param query Query SQL
     * @return entradas encontradas.
     */
    public final ExtendedObject[] select(Query query) {
        Message message = new Message("select_document");
        message.add("query", query);
        return call(message);
    }
    
    /**
     * 
     * @param query
     * @return
     */
    public final Map<Object, ExtendedObject> selectToMap(Query query) {
        Message message = new Message("select_to_map");
        message.add("query", query);
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
        Message message = new Message("unlock");
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
    public final int update(Query query) {
        Message message = new Message("update_document");
        message.add("query", query);
        return call(message);
    }
    
    /**
     * Atualiza uma entrada especificada por declaração SQL e critérios.
     * @param query declaração SQL
     * @param criteria critérios
     * @return quantidade de registros atualizados com sucesso.
     */
    public final int update(Query[] queries) {
        Message message = new Message("update_m");
        message.add("queries", queries);
        return call(message);
    }
    
    /**
     * Atualiza modelo de documento.
     * @param model dados do modelo de documento.
     * @return 1, se o modelo foi atualizado.
     */
    public final int updateModel(DocumentModel model) {
        Message message = new Message("update_model");
        message.add("model", model);
        return call(message);
    }
    
    /**
     * - not ready, do not use -
     * @param model
     * @return
     */
    public final int validate(DocumentModel model) {
        Message message = new Message("validate_model");
        message.add("model", model);
        return call(message);
    }
}