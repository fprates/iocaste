package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

/**
 * Serviços para gerenciar ajudas de pesquisas.
 * 
 * @author francisco.prates
 *
 */
public class SHLib extends AbstractServiceInterface {
    private static final String APP_NAME = "/iocaste-search-help/services.html";
    
    public SHLib(Function function) {
        initService(function, APP_NAME);
    }
    
    /**
     * Associa ajuda de pesquisa à um item de modelo.
     * @param item item de modelo
     */
    public final void assign(DocumentModelItem item) {
        Message message = new Message("assign");
        message.add("model_item", item);
        call(message);
    }
    
    /**
     * Retorna dados do ajuda de pesquisa.
     * @param name nome
     * @return dados da ajuda de pesquisa
     */
    public final SearchHelpData get(String name) {
        Message message = new Message("get");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Remove ajuda de pesquisa.
     * @param shname nome
     * @return 1, se removido com sucesso.
     */
    public final int remove(String name) {
        Message message = new Message("remove");
        message.add("shname", name);
        return call(message);
    }
    
    /**
     * Grava ajuda de pesquisa.
     * @param header dados do cabeçalho.
     * @param itens itens da ajuda
     */
    public final void save(SearchHelpData shd) {
        Message message = new Message("save");
        message.add("shdata", shd);
        call(message);
    }
    
    /**
     * Remove ajuda de pesquisa de um item de modelo.
     * @param shname nome
     * @return 1, se removido com sucesso
     */
    public final int unassign(DocumentModelItem item) {
        Message message = new Message("unassign");
        message.add("item", item);
        return call(message);
    }
    
    /**
     * Atualiza ajuda de pesquisa
     * @param header cabeçalho
     * @param itens itens
     */
    public final void update(ExtendedObject header, ExtendedObject... itens) {
        Message message = new Message("update");
        message.add("header", header);
        message.add("itens", itens);
        call(message);
    }

}
