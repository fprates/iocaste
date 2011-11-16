/*
    DocumentModel.java, implementação de modelo de documento.
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementação de modelo de documento
 * @author Francisco Prates
 *
 */
public class DocumentModel implements Comparable<DocumentModel>, Serializable {
    private static final long serialVersionUID = -4964159453586462503L;
    private String name;
    private String tablename;
    private String classname;
    private Set<DocumentModelItem> itens;
    private Set<DocumentModelKey> keys;
    private Map<String, String> queries;
    
    public DocumentModel() {
        itens = new TreeSet<DocumentModelItem>();
        keys = new TreeSet<DocumentModelKey>();
        queries = new HashMap<String, String>();
    }
    
    /**
     * Adiciona item ao documento.
     * @param item
     */
    public final void add(DocumentModelItem item) {
        itens.add(item);
    }
    
    /**
     * Adiciona chave ao documento.
     * @param key
     */
    public final void addKey(DocumentModelKey key) {
        keys.add(key);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(DocumentModel document) {
        if (document.equals(document))
            return 0;
        
        return name.compareTo(document.getName());
    }

    /**
     * 
     * @param item
     * @return
     */
    public final boolean contains(DocumentModelItem item) {
        for (DocumentModelItem modelitem : itens)
            if (item.getName().equals(modelitem.getName()))
                return true;
        
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        DocumentModel document;
        
        if (object == this)
            return true;
        
        if (!(object instanceof DocumentModel))
            return false;
        
        document = (DocumentModel)object;
        
        return name.equals(document.getName());
    }
    
    /**
     * 
     * @return
     */
    public final String getClassName() {
    	return classname;
    }
    
    /**
     * Retorna conjunto de itens do documento.
     * @return itens
     */
    public Set<DocumentModelItem> getItens() {
        return itens;
    }
    
    /**
     * Retorna itens chave do documento.
     * @return chaves
     */
    public Set<DocumentModelKey> getKeys() {
        return keys;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final DocumentModelItem getModelItem(String name) {
        for (DocumentModelItem item : itens)
            if (item.getName().equals(name))
                return item;
        
        return null;
    }
    
    /**
     * Retorna nome do documento.
     * @return nome
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getQuery(String name) {
        return queries.get(name);
    }
    
    /**
     * 
     * @return
     */
    public String getTableName() {
        return tablename;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (name == null)?0 : name.hashCode();
    }
    
    /**
     * Retorna true se o item do modelo for campo chave.
     * @param item item do modelo 
     * @return true, se item é chave
     */
    public final boolean isKey(DocumentModelItem item) {
        DocumentModelKey key = new DocumentModelKey();
        key.setModel(this);
        key.setModelItem(item.getName());
        
        return keys.contains(key);
    }
    
    /**
     * 
     * @param classname
     */
    public void setClassName(String classname) {
    	this.classname = classname;
    }
    
    /**
     * Define itens do documento.
     * @param itens
     */
    protected void setItens(Set<DocumentModelItem> itens) {
        this.itens = itens;
    }
    
    /**
     * Define chaves do documento.
     * @param keys chaves
     */
    protected void setKeys(Set<DocumentModelKey> keys) {
        this.keys = keys;
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
     * @param queries
     */
    public final void setQueries(Map<String, String> queries) {
        this.queries = queries;
    }
    
    /**
     * 
     * @param tablename
     */
    public void setTableName(String tablename) {
        this.tablename = tablename;
    }
}
