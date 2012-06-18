package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
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
     * @throws Exception
     */
    public final void assign(DocumentModelItem item) throws Exception {
        Message message = new Message();
        
        message.setId("assign");
        message.add("model_item", item);
        
        call(message);
    }
    
    /**
     * Retorna dados do ajuda de pesquisa.
     * @param name nome
     * @return dados da ajuda de pesquisa
     * @throws Exception
     */
    public final ExtendedObject[] get(String name) throws Exception {
        Message message = new Message();
        
        message.setId("get");
        message.add("name", name);
        
        return call(message);
    }
    
    /**
     * Remove ajuda de pesquisa.
     * @param shname nome
     * @return 1, se removido com sucesso.
     * @throws Exception
     */
    public final int remove(String name) throws Exception {
        Message message = new Message();
        
        message.setId("remove");
        message.add("shname", name);
        
        return call(message);
    }
    
    /**
     * Grava ajuda de pesquisa.
     * @param header dados do cabeçalho.
     * @param itens itens da ajuda
     * @throws Exception
     */
    public final void save(ExtendedObject header, ExtendedObject...itens)
            throws Exception {
        Message message = new Message();
        
        message.setId("save");
        message.add("header", header);
        message.add("itens", itens);
        
        call(message);
    }
    
    /**
     * Remove ajuda de pesquisa.
     * @param shname nome
     * @return 1, se removido com sucesso
     * @throws Exception
     */
    public final int unassign(String shname) throws Exception {
        Message message = new Message();
        
        message.setId("unassign");
        message.add("name", shname);
        
        return call(message);
    }
    
    /**
     * Atualiza ajuda de pesquisa
     * @param header cabeçalho
     * @param itens itens
     * @throws Exception
     */
    public final void update(ExtendedObject header, ExtendedObject... itens)
            throws Exception {
        Message message = new Message();
        
        message.setId("update");
        message.add("header", header);
        message.add("itens", itens);
        
        call(message);
    }

}
