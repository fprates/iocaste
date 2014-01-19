package org.iocaste.datadict;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

    public static InstallData self() {
        TaskGroup taskgroup;
        UserProfile profile;
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        authorization = new Authorization("DDICT.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-datadict");
        data.add(authorization);
        
        profile = new UserProfile("DEVELOP");
        profile.add(authorization);
        data.add(profile);
        
        data.link("SE11", "iocaste-datadict");
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("SE11");
        data.add(taskgroup);
        
        messages = new HashMap<>();
        messages.put("add", "Adicionar");
        messages.put("addshitem", "Adicionar");
        messages.put("choose.one.item",
                "Marque um item para obter seu detalhe.");
        messages.put("create", "Criar");
        messages.put("datadict-create", "Criar modelo");
        messages.put("datadict-selection", "Selecionar objeto do dicionário");
        messages.put("datadict-update", "Editar modelo");
        messages.put("datadict-view", "Exibir modelo");
        messages.put("delete", "Remover");
        messages.put("deleteitem", "Remover");
        messages.put("deleteshitem", "Remover");
        messages.put("EXPORT", "Exportar");
        messages.put("foreign.key", "Referência estrangeira");
        messages.put("generateclass", "Gerar classe");
        messages.put("ITEM", "Exibir");
        messages.put("item.classfield", "Atributo da classe");
        messages.put("item.dec", "Decimais");
        messages.put("item.element", "Elemento de dados");
        messages.put("item.key", "Chave");
        messages.put("item.length", "Comprimento");
        messages.put("item.name", "Item");
        messages.put("item.sh", "Item da ajuda de pesquisa");
        messages.put("item.tablefield", "Item tabela");
        messages.put("item.type", "Tipo de dados");
        messages.put("item.upcase", "Converte para maiúscula");
        messages.put("item-detail-editor", "Detalhes do item do object");
        messages.put("itemdetails", "Detalhe");
        messages.put("MODEL", "Modelo");
        messages.put("model.already.exist", "Modelo já existe");
        messages.put("model.name", "Modelo");
        messages.put("model.not.found", "Modelo não encontrado.");
        messages.put("model.removed.sucessfully",
                "Modelo removido com sucesso");
        messages.put("modelname", "Modelo");
        messages.put("modeltext", "Descrição");
        messages.put("modelclass", "Classe");
        messages.put("modeltable", "Tabela");
        messages.put("name", "Objeto");
        messages.put("NAME", "Nome");
        messages.put("reference.item", "Item referência");
        messages.put("reference.model", "Modelo referência");
        messages.put("rename", "Renomear");
        messages.put("save", "Salvar");
        messages.put("savesh", "Salvar");
        messages.put("SE11", "Editor de modelos de dados");
        messages.put("search.help", "Ajuda de pesquisa");
        messages.put("sh.not.found", "Ajuda de pesquisa não encontrada");
        messages.put("sh-editor-display", "Exibir ajuda de pesquisa");
        messages.put("sh-editor-create", "Criar ajuda de pesquisa");
        messages.put("sh-editor-update", "Editar ajuda de pesquisa");
        messages.put("sh.already.exist", "Ajuda de pesquisa já existe.");
        messages.put("show", "Exibir");
        messages.put("table", "Tabela");
        messages.put("table.saved.successfully", "Tabela salva com sucesso.");
        messages.put("technical.details", "Detalhes técnicos");
        messages.put("update", "Atualizar");
        data.setMessages("pt_BR", messages);
        
        return data;
    }
}
