package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValueRange implements Serializable {
    private static final long serialVersionUID = 7787626436215851626L;
    private List<ValueRangeItem> itens;
    
    public ValueRange() {
        itens = new ArrayList<ValueRangeItem>();
    }
    
    public final void add(ValueRangeItem item) {
        itens.add(item);
    }
    
    public final ValueRangeItem get(int index) {
        return itens.get(index);
    }
    
    public final ValueRangeItem[] getItens() {
        return itens.toArray(new ValueRangeItem[0]);
    }
    
}
