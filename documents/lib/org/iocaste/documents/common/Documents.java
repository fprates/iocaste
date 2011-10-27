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
    private Function function;
    
    public Documents(Function function) {
        this.function = function;
        initService(function, SERVERNAME);
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
        
        return (DocumentModel)call(message);
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
        
        return (Long)call(message);
    }
    
    /**
     * 
     * @param query
     * @return
     * @throws Exception 
     */
    public final Object[] select(String query, Object[] criteria) throws Exception {
        String select[];
        int t;
        StringBuilder sb = new StringBuilder("select ");
        Iocaste iocaste = new Iocaste(function);
        String[] parsed = query.split(" ");
        int pass = 0;
        
        for (String token : parsed) {
            switch (pass) {
            case 0:
                if (token.equals("select")) {
                    pass = 1;
                    continue;
                }
                
                if (token.equals("from")) {
                    pass = 3;
                    sb.append("* from ");
                    continue;
                }
                
                continue;
            case 1:
                select = token.split(",");
                t = select.length;
                
                for (int i = 0; i < t; i++) {
                    sb.append(select[i]);
                    if (i == (t - 1))
                        continue;
                    sb.append(",");
                }
                
                pass = 2;
                continue;
            case 2:
                if (token.equals("from"))
                    sb.append(" from ");
                
                pass = 3;
                continue;
            case 3:
                sb.append(getModel(token).getTableName());
                continue;
            }
        }
        
        return iocaste.select(sb.toString(), criteria);
    }
}
