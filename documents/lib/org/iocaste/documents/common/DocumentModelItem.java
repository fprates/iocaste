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
    private String attribname;
    private String gettername;
    private String settername;
    private String fieldname;
    private int index;
    
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

        return index - documentitem.getIndex();
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
    
    /**
     * Retorna nome do atributo.
     * @return nome do atributo
     */
    public String getAttributeName() {
        return attribname;
    }
    
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
     * Retorna nome do método getter.
     * @return nome do getter
     */
    public final String getGetterName() {
        return gettername;
    }
    
    /**
     * 
     * @return
     */
    public final int getIndex() {
    	return index;
    }
    
    /**
     * Retorna nome do item.
     * @return nome
     */
    public String getName() {
        return name;
    }
    
    /**
     * Retorna o nome do método setter.
     * @return nome do setter
     */
    public final String getSetterName() {
        return settername;
    }
    
    /**
     * 
     * @return
     */
    public final String getTableFieldName() {
    	return fieldname;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (name == null)? 0 : name.hashCode();
    }
    
    /**
     * Define nome do atributo.
     * @param attribname nome do atributo
     */
    public void setAttributeName(String attribname) {
        StringBuffer sb;
        this.attribname = attribname;
        
        if (attribname == null || attribname.equals("")) {
            gettername = null;
            settername = null;
            return;
        }
        
        sb = new StringBuffer("get").append(Character.toUpperCase(
                attribname.charAt(0))).append(attribname.substring(1));
        gettername = sb.toString();
        settername = gettername.replaceFirst("get", "set");
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
     * 
     * @param index
     */
    public final void setIndex(int index) {
    	this.index = index;
    }
    
    /**
     * Define nome do documento.
     * @param nome
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @param fieldname
     */
    public void setTableFieldName(String fieldname) {
    	this.fieldname = fieldname;
    }

}
