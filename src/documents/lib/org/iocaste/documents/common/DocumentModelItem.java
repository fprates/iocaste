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

import org.iocaste.protocol.IocasteException;

/**
 * Implementação do item do modelo de documento
 * @author francisco.prates
 *
 */
public class DocumentModelItem implements Comparable<DocumentModelItem>,
            Serializable {
    private static final long serialVersionUID = 7353680713818082301L;
    public static final int MAX_FNAME_LEN = 24;
    private DocumentModel document;
    private DataElement dataelement;
    private String name, attribname, gettername, settername, fieldname, sh;
    private String index;
    private DocumentModelItem reference;
    private boolean dummy;
    
    public DocumentModelItem(String name) {
        if (name == null)
            throw new RuntimeException("item name can not be null.");
        
        this.name = name.toUpperCase();
        dummy = false;
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
     * Posição do item no modelo.
     * @return posição
     */
    public final String getIndex() {
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
     * Retorna referência do item.
     * @return item de referência.
     */
    public final DocumentModelItem getReference() {
        return reference;
    }
    
    /**
     * Retorna o nome do método setter.
     * @return nome do setter
     */
    public final String getSetterName() {
        return settername;
    }
    
    /**
     * Retorna ajuda de pesquisa associada ao item.
     * @return nome da ajuda de pesquisa.
     */
    public final String getSearchHelp() {
        return sh;
    }
    
    /**
     * Retorna nome do campo da tabela.
     * @return nome do campo.
     */
    public final String getTableFieldName() {
    	return fieldname;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        return (name == null)? 0 : name.hashCode();
    }
    
    /**
     * 
     * @return
     */
    public final boolean isDummy() {
        return dummy;
    }
    
    /**
     * Define nome do atributo da classe.
     * @param attribname nome do atributo
     */
    public final void setAttributeName(String attribname) {
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
    public final void setDataElement(DataElement dataelement) {
        this.dataelement = dataelement;
    }
    
    /**
     * Define modelo de documento.
     * @param modelo de documento
     */
    public final void setDocumentModel(DocumentModel document) {
        if ((this.document != null) && !this.document.equals(document))
            throw new IocasteException(
                    "%s can't be assigned to another document model.", name);
        this.document = document;
    }
    
    /**
     * 
     * @param dummy
     */
    public final void setDummy(boolean dummy) {
        this.dummy = dummy;
    }
    
    /**
     * Define posição do item no modelo.
     * @param index posição.
     */
    public final void setIndex(String index) {
    	this.index = index;
    }
    
    /**
     * Define referência do item.
     * 
     * Pode ser utilizado como referência estrangeira em validação
     * de entrada de dados em tela.
     * 
     * @param reference item de referência.
     */
    public final void setReference(DocumentModelItem reference) {
        DocumentModel model;
        
        this.reference = reference;
        if (reference == null)
            return;
        
        model = reference.getDocumentModel();
        if (!model.isKey(reference))
            throw new RuntimeException(new StringBuilder("reference ").
                    append(model.getName()).append(".").
                    append(reference.getName()).append(" isn't model key.").
                    toString());
    }
    
    /**
     * Define ajuda de pesquisa para campo.
     * 
     * Se definido, acesso à ajuda de pesquisa será exibido
     * para campos de entrada em tela que tenham esse item associado.
     * 
     * @param sh nome da ajuda de pesquisa.
     */
    public final void setSearchHelp(String sh) {
        this.sh = (sh == null)? null : sh.toUpperCase();
    }
    
    /**
     * Define nome do campo de tabela associado ao item.
     * 
     * O campo será utilizado em operações com tabela.
     * 
     * @param fieldname
     */
    public void setTableFieldName(String fieldname) {
        this.fieldname = (fieldname == null)? null : fieldname.toUpperCase();
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        String docname;
        
        if (document == null)
            return name;
        
        docname = document.getName();
        if (docname == null)
            return name;
        
        return new StringBuilder(docname).append(".").append(name).toString();
    }

}
