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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementação de modelo de documento
 * 
 * Modelos de documentos são referências de tipos estruturados.
 * Modelos podem ser utilizados como base para formatação de objetos extendidos,
 * tabelas e componentes de visão.
 * 
 * @author Francisco Prates
 *
 */
public class DocumentModel implements Comparable<DocumentModel>, Serializable {
    private static final long serialVersionUID = -4964159453586462503L;
    private DocumentModelItem namespace;
    private String name, tablename, classname, pkgname;
    private Map<String, DocumentModelItem> itens;
    private Map<String, String> indexes;
    private Set<DocumentModelKey> keys;
    private boolean corrupted;
    
    public DocumentModel(String name) {
        this.name = name;
        itens = new LinkedHashMap<>();
        keys = new LinkedHashSet<>();
        indexes = new HashMap<>();
    }
    
    /**
     * Adiciona item ao documento.
     * @param item
     */
    public final void add(DocumentModelItem item) {
        String index = String.format("%s%03d", name, itens.size());
        String name = item.getName();
        
        item.setIndex(index);
        item.setDocumentModel(this);
        itens.put(name, item);
        indexes.put(index, name);
    }
    
    /**
     * Adiciona chave ao documento.
     * @param key
     */
    public final void add(DocumentModelKey key) {
        key.setModel(this);
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
     * Verifica se item faz parte do modelo.
     * @param item item do modelo.
     * @return true, se item faz parte do modelo.
     */
    public final boolean contains(DocumentModelItem item) {
        return itens.containsKey(item.getName());
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final boolean contains(String name) {
        return itens.containsKey(name);
    }
    
    public final void corrupted() {
        corrupted = true;
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
    
    public final DocumentModelItem getByIndex(String index) {
        return itens.get(indexes.get(index));
    }
    
    /**
     * Obtem a classe associada.
     * @return nome da classe.
     */
    public final String getClassName() {
    	return classname;
    }
    
    /**
     * Retorna conjunto de itens do documento.
     * @return itens
     */
    public final DocumentModelItem[] getItens() {
        DocumentModelItem item;
        int i = 0;
        DocumentModelItem[] ordered = new DocumentModelItem[itens.size()];
        
        for (String name : itens.keySet()) {
            item = itens.get(name);
            ordered[i++] = item;
        }
        
        return ordered;
    }
    
    /**
     * Retorna itens chave do documento.
     * @return chaves
     */
    public Set<DocumentModelKey> getKeys() {
        return keys;
    }
    
    /**
     * Retorna item de modelo especificado.
     * @param name nome do item.
     * @return item.
     */
    public final DocumentModelItem getModelItem(String name) {
        return itens.get(name);
    }
    
    /**
     * 
     * @return
     */
    public final DocumentModelItem getNamespace() {
        return namespace;
    }
    
    /**
     * Retorna nome do documento.
     * @return nome
     */
    public final String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public final String getPackage() {
        return pkgname;
    }
    
    /**
     * Obtem tabela do banco associada.
     * @return nome da tabela.
     */
    public final String getTableName() {
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

    public final boolean isCorrupted() {
        return corrupted;
    }
    
    /**
     * Indica se o item do modelo for campo chave.
     * @param item item do modelo 
     * @return true, se item é chave
     */
    public final boolean isKey(DocumentModelItem item) {
        DocumentModelKey key = new DocumentModelKey(item);
        key.setModel(this);
        
        return keys.contains(key);
    }
    
    /**
     * Define classe associada.
     * @param classname nome da classe.
     */
    public final void setClassName(String classname) {
    	this.classname = classname;
    }
    
    /**
     * Define itens do documento.
     * @param items itens
     */
    protected final void setItens(Set<DocumentModelItem> items) {
        itens.clear();
        for (DocumentModelItem item : items)
            itens.put(item.getName(), item);
    }
    
    /**
     * Define chaves do documento.
     * @param keys chaves
     */
    protected final void setKeys(Set<DocumentModelKey> keys) {
        this.keys = keys;
    }
    
    /**
     * 
     * @param element
     */
    public final void setNamespace(DocumentModelItem item) {
        namespace = item;
        if ((item != null) && (item.getDocumentModel() == null))
            namespace.setDocumentModel(this);
    }
    
    /**
     * 
     * @param pkgname
     */
    public final void setPackage(String pkgname) {
        this.pkgname = pkgname;
    }
    
    /**
     * Associa tabela do banco ao modelo.
     * @param tablename nome da tabela.
     */
    public final void setTableName(String tablename) {
        this.tablename = tablename;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return name;
    }
}
