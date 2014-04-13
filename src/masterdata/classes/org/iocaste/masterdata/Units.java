package org.iocaste.masterdata;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.SearchHelpData;

public class Units {

    public static final void init(InstallContext context) {
        DocumentModel model;
        DocumentModelItem item;
        DataElement element;
        SearchHelpData shd;
        
        /*
         * Unidades de medida
         */
        model = context.data.getModel("MEASURE_UNITS", "MUNITS", null);
        
        element = new DataElement("UNIT_ID");
        element.setType(DataType.CHAR);
        element.setLength(3);
        element.setUpcase(true);
        item = new DocumentModelItem("UNIT_ID");
        item.setTableFieldName("UNTID");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));

        element = new DataElement("UNIT_TEXT");
        element.setType(DataType.CHAR);
        element.setLength(30);
        element.setUpcase(false);
        item = new DocumentModelItem("TEXT");
        item.setTableFieldName("UNTTX");
        item.setDataElement(element);
        model.add(item);
        
        context.data.addValues(model, "BD", "Balde");
        context.data.addValues(model, "BL", "Bloco");
        context.data.addValues(model, "CX", "Caixa");
        context.data.addValues(model, "CM", "Centímetro");
        context.data.addValues(model, "CM3", "Centímetro cúbico");
        context.data.addValues(model, "CM2", "Centímetro quadrado");
        context.data.addValues(model, "CEN", "Cento");
        context.data.addValues(model, "CH", "Chapa");
        context.data.addValues(model, "CJ", "Conjunto");
        context.data.addValues(model, "DIA", "Dias");
        context.data.addValues(model, "DZ", "Dúzia");
        context.data.addValues(model, "FD", "Fardo");
        context.data.addValues(model, "FL", "Folha");
        context.data.addValues(model, "FR", "Frasco");
        context.data.addValues(model, "GAL", "Galão");
        context.data.addValues(model, "GR", "Garrafa");
        context.data.addValues(model, "G", "Grama");
        context.data.addValues(model, "HRS", "Horas");
        context.data.addValues(model, "JAR", "Jardas");
        context.data.addValues(model, "KML", "Kilometro/Litro");
        context.data.addValues(model, "LT", "Lata");
        context.data.addValues(model, "L", "Litro");
        context.data.addValues(model, "MH", "Máquina Hora");
        context.data.addValues(model, "MES", "Mês");
        context.data.addValues(model, "M", "Metro");
        context.data.addValues(model, "M3", "Metro cúbico");
        context.data.addValues(model, "M2", "Metro quadrado");
        context.data.addValues(model, "MI", "Milha");
        context.data.addValues(model, "MIL", "Milheiro");
        context.data.addValues(model, "MG", "Miligrama");
        context.data.addValues(model, "MM", "Milímetro");
        context.data.addValues(model, "MM3", "Milímetro cúbico");
        context.data.addValues(model, "MM2", "Milímetros quadrados");
        context.data.addValues(model, "MS", "Milisegundos");
        context.data.addValues(model, "MIN", "Minuto");
        context.data.addValues(model, "OZ", "Onça");
        context.data.addValues(model, "PAL", "Palete");
        context.data.addValues(model, "PAR", "Pares");
        context.data.addValues(model, "PC", "Peça");
        context.data.addValues(model, "PÉS", "PÉS");
        context.data.addValues(model, "POL", "Polegada");
        context.data.addValues(model, "KG", "Quilograma");
        context.data.addValues(model, "KM", "Quilômetro");
        context.data.addValues(model, "KM2", "Quilômetro quadrado");
        context.data.addValues(model, "KMH", "Quilômetros/hora");
        context.data.addValues(model, "RES", "Resma");
        context.data.addValues(model, "RL", "Rolo");
        context.data.addValues(model, "SAC", "Saco");
        context.data.addValues(model, "SC", "Saco");
        context.data.addValues(model, "TAM", "Tambor");
        context.data.addValues(model, "TB", "Tambor");
        context.data.addValues(model, "TO", "Tonelada");
        context.data.addValues(model, "UN", "Unidade");

        shd = new SearchHelpData("SH_MUNIT");
        shd.setModel("MEASURE_UNITS");
        shd.add("UNIT_ID");
        shd.add("TEXT");
        shd.setExport("UNIT_ID");
        context.data.add(shd);
    }
}
