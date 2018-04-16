/*  
    Form.java, implementação básica de formulário html.
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

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Implementação de formulário HTML.
 * 
 * @author Francisco de Assis Prates
 *
 */
public class Form extends ToolDataElement {
    private static final long serialVersionUID = -4049409929220114810L;
    public static final String MULTIPART_ENCODE = "multipart/form-data";
    
    public Form(View view, String name) {
        this(new ElementViewContext(view, null, TYPES.FORM, name), name);
    }
    
    public Form(Container container, String name) {
        this(new ElementViewContext(null, container, TYPES.FORM, name), name);
    }

    public Form(Context viewctx, String name) {
        super(viewctx, Const.FORM, name);
        String action = name.concat(".action");
        new Parameter(this, action);
        setAction(action);
    }
    
    /**
     * Retorna o tipo de codificação.
     * @return codificação
     */
    public final String getEnctype() {
        return tooldata.attributes.get("enctype");
    }
    
    /**
     * Ajusta o tipo de codificação.
     * @param enctype codificação
     */
    public final void setEnctype(String enctype) {
        tooldata.attributes.put("enctype", enctype);
    }
}
