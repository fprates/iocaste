package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class Install {

    /**
     * 
     * @param data
     * @return
     */
    private static final DocumentModel installLanguages(InstallData data) {
        int i;
        String tag, country;
        DocumentModelItem item;
        DocumentModel model = data.getModel("LANGUAGES", "LANG", "");
        DataElement element = new DataElement();
        
        element.setName("LANGUAGES.LOCALE");
        element.setType(DataType.CHAR);
        element.setLength(5);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("LOCALE");
        item.setDataElement(element);
        item.setTableFieldName("LOCAL");
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DataElement();
        element.setName("LANGUAGES.CODE");
        element.setType(DataType.NUMC);
        element.setLength(3);
        
        item = new DocumentModelItem();
        item.setName("CODE");
        item.setDataElement(element);
        item.setTableFieldName("LCODE");
        
        model.add(item);
        
        i = 1;
        for (Locale locale : Locale.getAvailableLocales()) {
            country = locale.getCountry();
            if (country.length() == 0)
                continue;
            
            tag = new StringBuilder(locale.getLanguage()).append("_").
                    append(country).toString();
            data.addValues(model, tag, i++);
        }
        
        return model;
    }
    
    /**
     * 
     * @param data
     * @param languages
     * @param function
     * @throws Exception
     */
    private static final void installMessages(InstallData data,
            DocumentModel languages, Function function) throws Exception {
        Documents documents;
        DocumentModelItem item, reference;
        DocumentModel model = data.getModel("MESSAGES", "MSGSRC", "");
        DataElement element = new DataElement();
        
        /*
         * índice
         */
        element.setName("INDEX");
        element.setType(DataType.NUMC);
        element.setLength(13);
        
        item = new DocumentModelItem();
        item.setName("INDEX");
        item.setDataElement(element);
        item.setTableFieldName("MSGNR");
        
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        /*
         * nome
         */
        element = new DataElement();
        element.setName("MESSAGES.NAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("NAME");
        item.setDataElement(element);
        item.setTableFieldName("MSGNM");
        
        model.add(item);
        
        /*
         * localização
         */
        reference = languages.getModelItem("LOCALE");
        element = reference.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("LOCALE");
        item.setDataElement(element);
        item.setTableFieldName("LOCAL");
        item.setReference(reference);
        
        model.add(item);
        
        /*
         * pacote
         */
        documents = new Documents(function);
        reference = documents.getModel("PACKAGE").getModelItem("NAME"); 
        element = reference.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("PACKAGE");
        item.setDataElement(element);
        item.setTableFieldName("PKGNM");
        item.setReference(reference);
        
        model.add(item);
        
        /*
         * mensagem
         */
        element = new DataElement();
        element.setName("MESSAGES.TEXT");
        element.setType(DataType.CHAR);
        element.setLength(255);
        element.setUpcase(false);
        
        item = new DocumentModelItem();
        item.setName("TEXT");
        item.setDataElement(element);
        item.setTableFieldName("MSGTX");
        
        model.add(item);
    }
    
    /**
     * 
     * @param function
     * @return
     * @throws Exception
     */
    public static final InstallData self(Function function) throws Exception {
        Map<String, String> messages;
        InstallData data = new InstallData();
        DocumentModel languages = installLanguages(data);
        
        installMessages(data, languages, function);
        
        messages = new HashMap<String, String>();
        messages.put("package-manager", "Gerenciador de pacotes");
        
        data.setMessages("pt_BR", messages);
        data.link("PACKAGE", "iocaste-packagetool");
        data.addTaskGroup("ADMIN", "PACKAGE");
        
        return data;
    }
}
