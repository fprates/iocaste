package org.iocaste.dataview;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData self() {
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        authorization = new Authorization("DATAVIEWER.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-dataview");
        data.add(authorization);
        
        messages = new HashMap<String, String>();
        messages.put("dataview-selection", "Seleção de modelo");
        messages.put("model.name", "Modelo");
        messages.put("edit", "Editar");
        messages.put("save", "Salvar");
        messages.put("insert", "Inserir");
        messages.put("delete", "Remover");
        messages.put("SE16", "Visão de entradas em modelos");
        data.setMessages("pt_BR", messages);
        
        data.link("SE16", "iocaste-dataview");
        data.addTaskGroup("DEVELOP", "SE16");
        
        return data;
    }
}
