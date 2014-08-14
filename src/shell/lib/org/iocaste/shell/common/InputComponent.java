package org.iocaste.shell.common;

import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

/**
 * Interface para componente de entrada.
 * 
 * A principal implementação é AbstractInputComponent.
 * 
 * @author francisco.prates
 *
 */
public interface InputComponent extends Component {
    
    /**
     * Retorna conteúdo do componente.
     * @return conteúdo
     */
    public abstract <T> T get();

    /**
     * Retorna tipo de componente.
     * @return tipo
     */
    public abstract Const getComponentType();
    
    /**
     * Retorna elemento de dados associado.
     * @return elemento de dados
     */
    public abstract DataElement getDataElement();

    /**
     * Retorna valor numérico do componente.
     * @return valor numérico em byte.
     */
    public abstract byte getb();
    
    /**
     * Retorna valor numérico do componente.
     * @return valor em ponto flutuante de precisão dupla.
     */
    public abstract double getd();
    
    /**
     * Retorna valor numérico do componente.
     * @return valor numérico em inteiro.
     */
    public abstract int geti();
    
    /**
     * Retorna valor numérico do componente.
     * @return valor numérico em inteiro longo.
     */
    public abstract long getl();
    
    /**
     * Retorna comprimento máximo de entrada.
     * @return comprimento
     */
    public abstract int getLength();
    
    /**
     * Retorna item do modelo associado.
     * @return item do modelo
     */
    public abstract DocumentModelItem getModelItem();

    /**
     * Retorna ajuda de pesquisa associada.
     * @return ajuda de pesquisa
     */
    public abstract SearchHelp getSearchHelp();
    
    /**
     * 
     * @return
     */
    public abstract String getst();
    
    /**
     * 
     * @return
     */
    public abstract Set<InputComponent> getStackComponents();
    
    /**
     * 
     * @return
     */
    public abstract int getVisibleLength();
    
    /**
     * Indica se é o valor é booleano.
     * @return true, se for componente booleano
     */
    public abstract boolean isBooleanComponent();
    
    /**
     * Indifica se a entrada é obrigatória.
     * @return
     */
    public abstract boolean isObligatory();
    
    /**
     * Indica se a exibição do valor é oculta.
     * @return true, se exibição oculta.
     */
    public abstract boolean isSecret();
    
    /**
     * Indica se o componente tem valores selecionáveis.
     * @return
     */
    public abstract boolean isSelectable();
    
    /**
     * Indica se o valor do componente é verdadeiro.
     * @return true, se o valor é verdadeiro.
     */
    public abstract boolean isSelected();
    
    /**
     * 
     * @return
     */
    public abstract boolean isStackable();
    
    /**
     * Indica se componente aceita faixa de valores.
     * @return true, se retorna faixa de valores.
     */
    public abstract boolean isValueRangeComponent();
    
    /**
     * Ajusta o valor do componente.
     * @param value valor
     */
    public abstract void set(Object value);
    
    
    /**
     * Ajusta o tipo do componente.
     * 
     * Por exemplo, um componente pode ser DataItem, mas é exibido como
     * um campo texto ou caixa de verificação.
     * 
     * @param type
     */
    public abstract void setComponentType(Const type);
    
    /**
     * Define um elemento de dados para o componente.
     * 
     * As entradas serão validadas a partir desse elemento.
     * 
     * @param dataelement
     */
    public abstract void setDataElement(DataElement dataelement);
    
    /**
     * Define comprimento máximo (em caracteres) da entrada.
     * @param length comprimento
     */
    public abstract void setLength(int length);
    
    /**
     * Define um item de modelo.
     * 
     * As entradas serão validadas a partir do elemento de dados
     * associado a esse item.
     * 
     * @param modelitem item de modelo
     */
    public abstract void setModelItem(DocumentModelItem modelitem);
    
    /**
     * Define se campo é obrigatório.
     * @param obligatory true, para campo obrigatório
     */
    public abstract void setObligatory(boolean obligatory);
    
    /**
     * Define ajuda de pesquisa para esse campo.
     * @param search nome da ajuda.
     */
    public abstract void setSearchHelp(SearchHelp search);
    
    /**
     * Oculta entrada de dados.
     * @param secret
     */
    public abstract void setSecret(boolean secret);
    
    /**
     * Marca entrada como true (em caso de componentes booleanos).
     * @param selected true, para valor verdadeiro
     */
    public abstract void setSelected(boolean selected);
    
    /**
     * 
     * @param vlength
     */
    public abstract void setVisibleLength(int vlength);
}
