package org.iocaste.shell.common;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

/**
 * Serviços do shell.
 * 
 * @author francisco.prates
 *
 */
public class Shell extends AbstractServiceInterface {
    public static final String SERVER_NAME = "/iocaste-shell/services.html";
    
    public Shell(Function function) {
        initService(function, SERVER_NAME);
    }
    
    /**
     * Copia componentes de entrada.
     * 
     * @param container conteiner
     * @param inputitem componente original
     * @param name nome do componente destino
     * @param values valores (para listbox)
     * @return componente de destino
     */
    public static final InputComponent copyInputItem(Container container,
            InputComponent inputitem, String name, Map<String, Object> values) {
        TextField tfield;
        CheckBox cbox;
        ListBox lbox;
        
        switch (inputitem.getComponentType()) {
        case TEXT_FIELD:
            tfield = new TextField(container, name);
            tfield.setObligatory(inputitem.isObligatory());
            tfield.setSecret(inputitem.isSecret());
            tfield.setLength(inputitem.getLength());
            tfield.set(inputitem.get());
            tfield.setModelItem(inputitem.getModelItem());
            tfield.setEnabled(inputitem.isEnabled());
            tfield.setDataElement(inputitem.getDataElement());
            tfield.setSearchHelp(inputitem.getSearchHelp());
            tfield.setLocale(inputitem.getLocale());
            tfield.setHtmlName(inputitem.getHtmlName());
            
            return tfield;
        case CHECKBOX:
            cbox = new CheckBox(container, name);
            cbox.set(inputitem.get());
            cbox.setModelItem(inputitem.getModelItem());
            cbox.setEnabled(inputitem.isEnabled());
            cbox.setDataElement(inputitem.getDataElement());
            cbox.setLocale(inputitem.getLocale());
            cbox.setHtmlName(inputitem.getHtmlName());
            
            return cbox;
            
        case LIST_BOX:
            lbox = new ListBox(container, name);
            lbox.set(inputitem.get());
            lbox.setModelItem(inputitem.getModelItem());
            lbox.setEnabled(inputitem.isEnabled());
            lbox.setDataElement(inputitem.getDataElement());
            lbox.setLocale(inputitem.getLocale());
            lbox.setHtmlName(inputitem.getHtmlName());
            
            for (String key : values.keySet())
                lbox.add(key, values.get(key));
            
            return lbox;
            
        default:
            return null;
        }
    }
    
    /**
     * Gera um componente especificado.
     * 
     * @param container
     * @param type
     * @param name
     * @param args
     * @return
     */
    public static final Element factory(Container container, Const type,
            String name, Object... args) {
        switch (type) {
        case BUTTON:
            return new Button(container, name);
            
        case CHECKBOX:
            return new CheckBox(container, name);
            
        case DATA_FORM:
            return new DataForm(container, name);
            
        case DATA_ITEM:
            return new DataItem((DataForm)container, (Const)args[0], name);
            
        case FORM:
            return new Form(container, name);
        
        case LINK:
            return new Link(container, name, (String)args[0]);
            
        case LIST_BOX:
            return new ListBox(container, name);
            
        case TABLE:
            return new Table(container, name);
            
        case STANDARD_CONTAINER:
            return new StandardContainer(container, name);
            
        case TEXT:
            return new Text(container, name);
            
        case TEXT_FIELD:
            return new TextField(container, name);
            
        default:
            return null;
        }
    }

    /**
     * Retorna elemento de dados de um componente de entrada.
     * @param input componente de entrada.
     * @return elemento de dados.
     */
    public static final DataElement getDataElement(InputComponent input) {
        DocumentModelItem modelitem = input.getModelItem();
        
        return (modelitem == null)? input.getDataElement() : 
            modelitem.getDataElement();
    }
    
    /**
     * Retorna folha de estilo associada à visão.
     * @param view visão
     * @return mapa com dados da folha de estilo.
     * @throws Exception
     */
    public final Map<String, Map<String, String>> getStyleSheet(View view)
            throws Exception {
        Message message = new Message();
        
        message.setId("get_style_sheet");
        message.add("appname", view.getAppName());
        
        return call(message);
    }
    
    /**
     * Retorna visão especificada.
     * @param view visão atual
     * @param pagename nome da visão
     * @return visão especificada.
     * @throws Exception
     */
    public final View getView(View view, String pagename)
            throws Exception {
        Message message = new Message();
        
        message.setId("get_view");
        message.add("app_name", view.getAppName());
        message.add("page_name", pagename);
        
        return call(message);
    }
    
    /**
     * Retorna à página inicial.
     * @param view
     * @return
     * @throws Exception
     */
    public final String[] home(View view) throws Exception {
        Message message = new Message();
        
        message.setId("home");
        
        return call(message);
    }
    
    /**
     * Verifica se uma string é inicial.
     * @param value string
     * @return true, se string for inicial.
     */
    public static final boolean isInitial(String value) {
        return (value == null || value.trim().length() == 0)? true : false;
    }
    
    /**
     * Restaura página anterior.
     * @param view visão atual
     * @return dados da página anterior
     * @throws Exception
     */
    public final String[] popPage(View view) throws Exception {
        Message message = new Message();
        
        message.setId("pop_page");
        
        return call(message);
    }
    
    /**
     * Salva página na pilha de páginas.
     * @param view visão.
     * @throws Exception
     */
    public final void pushPage(View view) throws Exception {
        Message message = new Message();
        
        message.setId("push_page");
        message.add("app_name", view.getAppName());
        message.add("page_name", view.getPageName());
        
        call(message);
    }
    
    /**
     * Atualiza visão na pilha de páginas.
     * @param view visão.
     * @throws Exception
     */
    public final void updateView(View view) throws Exception {
        Message message = new Message();
        
        message.setId("update_view");
        message.add("view", view);
        
        call(message);
    }
}
