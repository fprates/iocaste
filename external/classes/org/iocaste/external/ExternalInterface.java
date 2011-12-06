package org.iocaste.external;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface ExternalInterface extends Library {

    public Pointer icst_get_container(Pointer iterator);
 
    public String icst_get_view_title(Pointer view);

    public Pointer icst_get_container_iterator(Pointer view);
    
    public boolean icst_has_container(Pointer iterator);
    
    public Pointer icst_ini_program(String name, String progpath,
            String icstpath);
    
    public Pointer icst_ini_view(Pointer program, String path);
}
