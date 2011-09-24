/*
    DocumentModelItem.java, implementação do item do modelo de documento
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

import java.io.Serializable;

/**
 * Implementação do item do modelo de documento
 * @author francisco.prates
 *
 */
public class DocumentModelItem implements Comparable<DocumentModelItem>,
            Serializable {
    private static final long serialVersionUID = 7353680713818082301L;
    private String name;
    private DocumentModel document;
    private DataElement dataelement;
    
    /**
     * Retorna elemento de dado corresponddente.
     * @return elemento de dado
     */
    public DataElement getDataElement() {
        return dataelement;
    }
    
    /**
     * Retorna modelo de documento correspondente.
     * @return modelo de documento
     */
    public DocumentModel getDocumentModel() {
        return document;
    }
    
    /**
     * Retorna nome do item.
     * @return nome
     */
    public String getName() {
        return name;
    }
    
    /**
     * Define elemento de dado.
     * @param elemento de dado
     */
    public void setDataElement(DataElement dataelement) {
        this.dataelement = dataelement;
    }
    
    /**
     * Define modelo de documento.
     * @param modelo de documento
     */
    public void setDocumentModel(DocumentModel document) {
        this.document = document;
    }
    
    /**
     * Define nome do documento.
     * @param nome
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        DocumentModelItem documentitem;
        
        if (object == this)
            return true;
        
        if (!(object instanceof DocumentModelItem))
            return false;
        
        documentitem = (DocumentModelItem)object;
        if (!documentitem.getDocumentModel().equals(document))
            return false;
        
        if (!name.equals(documentitem.getName()))
            return false;
        
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (name == null)
            return 0;
        
        return (11 * document.hashCode()) + name.hashCode();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DocumentModelItem documentitem) {
        int compare;
        
        if (documentitem.equals(this))
            return 0;
        
        compare = document.compareTo(documentitem.getDocumentModel());
        
        if (compare != 0)
            return compare;

        return name.compareTo(documentitem.getName());
    }

}
