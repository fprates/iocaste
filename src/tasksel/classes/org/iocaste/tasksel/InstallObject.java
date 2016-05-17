package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class InstallObject extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages;
        ModelInstall model;
        DataElement group, name, text;
        
        messages = messageInstance("pt_BR");
        messages.put("command", "Comando");
        messages.put("command.not.found", "Comando não encontrado.");
        messages.put("not.authorized", "Sem autorização para executar.");
        messages.put("run", "Executar");
        messages.put("iocaste-tasksel", "Seletor de tarefas");

        group = elementchar("TASK_TILE_GROUP", 64, DataType.UPPERCASE);
        name = elementchar("TASK_TILE_NAME", 64, DataType.UPPERCASE);
        text = elementchar("TASK_TILE_TEXT", 128, DataType.KEEPCASE);
        
        model = modelInstance("TASK_TILE_ENTRY");
        model.item("GROUP", group);
        model.item("NAME", name);
        model.item("TEXT", text);
    }
}

