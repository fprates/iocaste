package org.iocaste.external.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages;
        
        messages = messageInstance("pt_BR");
        messages.put("acceptfunctions_table", "Aceitar");
        messages.put("acceptparameters_table", "Aceitar");
        messages.put("addfunctions_table", "Adicionar");
        messages.put("addparameters_table", "Adicionar");
        messages.put("removefunctions_table", "Remover");
        messages.put("removeparameters_table", "Remover");
        messages.put("externalconncreate", "Criar porta");
        messages.put("externalconncreate1", "Criar porta");
        messages.put("externalconndisplay", "Exibir porta");
        messages.put("externalconndisplay1", "Exibir porta");
        messages.put("externalconnedit", "Editar porta");
        messages.put("externalconnedit1", "Editar porta");
        messages.put("externalfunctioncreate", "Criar função");
        messages.put("externalfunctioncreate1", "Criar função");
        messages.put("externalfunctiondisplay", "Exibir função");
        messages.put("externalfunctiondisplay1", "Exibir função");
        messages.put("externalfunctionedit", "Editar função");
        messages.put("externalfunctionedit1", "Editar função");
        messages.put("externalstructcreate", "Criar estrutura");
        messages.put("externalstructcreate1", "Criar estrutura");
        messages.put("externalstructdisplay", "Exibir estrutura");
        messages.put("externalstructdisplay1", "Exibir estrutura");
        messages.put("externalstructedit", "Editar estrutura");
        messages.put("externalstructedit1", "Editar estrutura");
        messages.put("FUNCTION", "Função (externa)");
        messages.put("functions", "Funções");
        messages.put("HOST", "Endereço servidor");
        messages.put("MODEL_ID", "Função");
        messages.put("NAME", "Nome");
        messages.put("PORT_NAME", "Porta");
        messages.put("PORT_TYPE", "Tipo de porta");
        messages.put("SAP_GWHOST", "Endereço gateway (SAP)");
        messages.put("SAP_GWSERVER", "Porta gateway (SAP)");
        messages.put("SAP_PROGRAM_ID", "ID Programa (SAP)");
        messages.put("SAP_CLIENT", "Cliente (SAP)");
        messages.put("SAP_SYSTEM_NUMBER", "Nº sistema (SAP)");
        messages.put("SECRET", "Senha");
        messages.put("SERVICE", "Serviço (iocaste)");
        messages.put("SERVICE_FUNCTION", "Função do seviço (iocaste)");
        messages.put("STRUCTURE", "Estrutura");
        messages.put("USERNAME", "Usuário");
        messages.put("XTRNLFNCCH", "Alterar função");
        messages.put("XTRNLFNCCR", "Criar função");
        messages.put("XTRNLFNCDS", "Exibir função");
        messages.put("XTRNLPORTCH", "Alterar porta");
        messages.put("XTRNLPORTCR", "Criar porta");
        messages.put("XTRNLPORTDS", "Exibir porta");
        messages.put("XTRNLSTRCH", "Alterar estrutura");
        messages.put("XTRNLSTRCR", "Criar estrutura");
        messages.put("XTRNLSTRDS", "Exibir estrutura");
            
        
    }

}
