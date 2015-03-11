package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValueRange implements Serializable {
    private static final long serialVersionUID = 7787626436215851626L;
    private List<ValueRangeItem> itens;
    
    public ValueRange() {
        itens = new ArrayList<>();
    }
    
    public final void add(ValueRangeItem item) {
        itens.add(item);
    }
    
    public final ValueRangeItem get(int index) {
        return itens.get(index);
    }
    
    public final List<ValueRangeItem> getItens() {
        return itens;
    }
    
    public final int length() {
        return itens.size();
    }
}
