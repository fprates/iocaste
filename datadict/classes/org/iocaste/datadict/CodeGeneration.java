package org.iocaste.datadict;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public class CodeGeneration {

    public static final void main(Context context) {
        String settype;
        DataForm form = context.view.getElement("structure.form");
        Table itens = context.view.getElement("itens");
        String value, classname = form.get("modelclass").get();
        String[] parts = classname.split("\\.");
        StringBuilder sb = new StringBuilder("package ");
        StringBuilder getter = new StringBuilder();
        StringBuilder setter = new StringBuilder();
        int t = parts.length - 1;
        List<String> getters = new ArrayList<>();
        List<String> setters = new ArrayList<>();

        context.code = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            if (i == t) {
                classname = parts[i];
                continue;
            }
            
            sb.append(parts[i]);
            sb.append((i == (t - 1))? ";" : ".");
        }
        
        context.code.add(sb.toString());
        context.code.add("");
        
        sb.setLength(0);
        sb.append("public class ").append(classname).append(" {");
        context.code.add(sb.toString());
        
        for (TableItem item : itens.getItems()) {
            sb.setLength(0);
            getter.setLength(0);
            setter.setLength(0);
            
            value = Common.getTableInput(item, "item.type").get();
            if (context.mode == Common.SHOW) {
                if (value.equals("char"))
                    value = "0";
                
                if (value.equals("numc"))
                    value = "3";
            }
            
            switch (Integer.parseInt(value)) {
            case 0:
                sb.append("    private String ");
                getter.append("    public final String get");
                setter.append("    public final void set");
                settype = "String";
                
                break;
            case 3:
                sb.append("    private int ");
                getter.append("    public final int get");
                setter.append("    public final void set");
                settype = "int";
                
                break;
            default:
                settype = "Object";
                break;
            }
            
            value = Common.getTableInput(item, "item.classfield").get();
            sb.append(value).append(";");
            context.code.add(sb.toString());
            
            /*
             *  public final ? get?() {
             *  return ?;
             *  }
             */
            getter.append(value.substring(0, 1).toUpperCase()).
                    append(value.substring(1)).append("() {");            
            getters.add(getter.toString());
            
            getter.setLength(0);
            getter.append("        return ").append(value).append(";");
            getters.add(getter.toString());
            getters.add("    }");
            getters.add("");
            
            /*
             * public final void set?(? ?) {
             * this.? = ?;
             * }
             */
            setter.append(value.substring(0, 1).toUpperCase()).
                    append(value.substring(1)).append("(").append(settype).
                    append(" ").append(value).append(") {");            
            setters.add(setter.toString());
            
            setter.setLength(0);
            setter.append("        this.").append(value).append(" = ").
                    append(value).append(";");
            setters.add(setter.toString());
            setters.add("    }");
            setters.add("");
        }
        
        sb.setLength(0);
        sb.append("    public ").append(classname).append("() { }");
        
        context.code.add("");
        context.code.add(sb.toString());
        context.code.add("");
        context.code.addAll(getters);
        context.code.addAll(setters);
        context.code.add("}");
        context.view.redirect("list");
    }
}
