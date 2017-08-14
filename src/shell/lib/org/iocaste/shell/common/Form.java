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

/**
 * Implementação de formulário HTML.
 * 
 * @author Francisco de Assis Prates
 *
 */
public class Form extends AbstractContainer {
    private static final long serialVersionUID = -4049409929220114810L;
    public static final String MULTIPART_ENCODE = "multipart/form-data";
    private String enctype, action;
    
    public Form(View view, String name) {
        super(view, Const.FORM, name);
        init(name);
    }
    
    public Form(Container container, String name) {
        super(container, Const.FORM, name);
        init(name);
    }

    private final void init(String name) {
        Parameter parameter = new Parameter(this, "action");
        parameter.setHtmlName(action = name.concat(".action"));
    }
    
    /**
     * Retorna o tipo de codificação.
     * @return codificação
     */
    public final String getEnctype() {
        return enctype;
    }
    
    /**
     * Ajusta o tipo de codificação.
     * @param enctype codificação
     */
    public final void setEnctype(String enctype) {
        this.enctype = enctype;
    }
    
    /**
     * 
     * @return
     */
    public final String getAction() {
        return action;
    }
}
