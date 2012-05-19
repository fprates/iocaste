package org.iocaste.datadict;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;

public class Install {

    public static InstallData self() {
        Map<String, String> messages;
        InstallData data = new InstallData();
        
        data.link("SE11", "iocaste-datadict");
        data.link("DATADICT", "iocaste-datadict");
        
        messages = new HashMap<String, String>();
        messages.put("datadict-selection", "Selecionar objeto do dicionário");
        messages.put("table", "Tabela");
        messages.put("search.help", "Ajuda de pesquisa");
        messages.put("create", "Criar");
        messages.put("show", "Exibir");
        messages.put("update", "Atualizar");
        messages.put("delete", "Remover");
        messages.put("rename", "Renomear");
        messages.put("modelname", "Modelo");
        messages.put("modeltext", "Descrição");
        messages.put("modelclass", "Classe");
        messages.put("modeltable", "Tabela");
        messages.put("itemdetails", "Detalhe");
        messages.put("item.name", "Item modelo");
        messages.put("item.tablefield", "Item tabela");
        messages.put("item.key", "Chave");
        messages.put("item.element", "Elemento de dados");
        messages.put("item.type", "Tipo de dados");
        messages.put("item.length", "Comprimento");
        messages.put("item.dec", "Decimais");
        messages.put("generateclass", "Gerar classe");
        messages.put("datadict-view", "Exibir objeto do dicionário");
        messages.put("item-detail-editor", "Detalhes do item do object");
        messages.put("foreign.key", "Referência estrangeira");
        messages.put("model.name", "Modelo");
        messages.put("item.name", "Item");
        messages.put("reference.model", "Modelo referência");
        messages.put("reference.item", "Item referência");
        messages.put("technical.details", "Detalhes técnicos");
        messages.put("item.classfield", "Atributo da classe");
        messages.put("item.sh", "Item da ajuda de pesquisa");
        messages.put("item.upcase", "Converte para maiúscula");
        messages.put("NAME", "Nome");
        messages.put("MODEL", "Modelo");
        messages.put("EXPORT", "Exportar");
        messages.put("ITEM", "Exibir");
        messages.put("sh-editor-display", "Exibir ajuda de pesquisa");
        messages.put("savesh", "Salvar");
        messages.put("addshitem", "Adicionar");
        messages.put("deleteshitem", "Remover");
        messages.put("choose.one.item",
                "Marque um item para obter seu detalhe.");
        messages.put("sh.not.found", "Ajuda de pesquisa não encontrada");
        
        data.setMessages("pt_BR", messages);
        
        return data;
    }
}
