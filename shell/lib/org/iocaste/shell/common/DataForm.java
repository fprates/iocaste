/*  
    DataForm.java, implementação de formulário de dados.
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

package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.DocumentModelItem;

/**
 * Implementação de formulário de dados.
 * 
 * @author Francisco de Assis Prates
 *
 */
public class DataForm extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private List<String> actions;
    
    public DataForm(Container container, String name) {
        super(container, Const.DATA_FORM, name);
        
        actions = new ArrayList<String>();
    }
    
    /**
     * Adiciona uma ação ao formulário.
     * @param action ação
     */
    public final void addAction(String action) {
        actions.add(action);
    }
    
    /**
     * Exporta dados do formulário para um objeto.
     * @param object objeto
     */
    public final void exportTo(Object object) {
        InputComponent item;
        String formmethodname;
        String objmethodname;
        String name;
        Method method_;
        DocumentModelItem modelitem;
        
        for (Element element: getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (InputComponent)element;
            modelitem = item.getModelItem();

            if (modelitem == null)
                continue;
            
            name = modelitem.getName();
            
            formmethodname = new StringBuffer("set").
                    append(name).toString().toLowerCase();
            method_ = null;
            
            for (Method method : object.getClass().getMethods()) {
                objmethodname = method.getName().toLowerCase();
                
                if (!objmethodname.equals(formmethodname))
                    continue;
                
                method_ = method;
                break;
            }
            
            if (method_ == null)
                continue;

            for (Class<?> class_ : method_.getParameterTypes()) {
                invokeCopy(class_, object, method_, item);
                break;
            }
        }
    }
    
    /**
     * Retorna ações associadas ao formulário.
     * @return ações
     */
    public final String[] getActions() {
        return actions.toArray(new String[0]);
    }
    
    /**
     * 
     * @param class_
     * @param object
     * @param method
     * @param component
     * @throws RuntimeException
     */
    private final void invokeCopy(Class<?> class_, Object object,
            Method method, InputComponent component) throws RuntimeException {
        String value = component.getValue();
        String typename = class_.getSimpleName();
        
        try {
            if (typename.equals("String")) {
                method.invoke(object, value);
                return;
            }
            
            if (typename.equals("Integer")) {
                method.invoke(object, Integer.parseInt(value));
                return;
            }
            
            if (typename.equals("Boolean")) {
                method.invoke(object, Boolean.parseBoolean(value));
                return;
            }
            
            if (typename.equals("Character")) {
                method.invoke(object, value.toCharArray()[0]);
                return;
            }
            
            if (typename.equals("Short")) {
                method.invoke(object, Short.parseShort(value));
                return;
            }
            
            if (typename.equals("Float")) {
                method.invoke(object, Float.parseFloat(value));
                return;
            }
            
            if (typename.equals("Double")) {
                method.invoke(object, Double.parseDouble(value));
                return;
            }
            
            if (typename.equals("Byte")) {
                method.invoke(object, Byte.parseByte(value));
                return;
            }
        } catch (Exception e) {
            value = new StringBuffer("Error loading parameter for ").
                    append(method.getName()).append(".").toString();
            throw new RuntimeException(value, e);
        }
    }
}
