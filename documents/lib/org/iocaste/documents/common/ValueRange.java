package org.iocaste.documents.common;

import java.util.ArrayList;
import java.util.List;

public class ValueRange {
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
