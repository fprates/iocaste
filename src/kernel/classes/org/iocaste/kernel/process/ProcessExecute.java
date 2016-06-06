package org.iocaste.kernel.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.kernel.files.FileServices;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class ProcessExecute extends AbstractHandler {

    private final Object[] getOutput(BufferedReader reader) throws IOException {
        String line;
        List<String> out = new ArrayList<>();
        
        while ((line = reader.readLine()) != null)
            out.add(line);
        reader.close();
        return (out.size() > 0)? out.toArray(new String[0]) : null;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Process process;
        BufferedReader inputreader, errorreader;
        Object[] output;
        String program = FileServices.composeFileName(
                System.getProperty("user.home"), "utils",
                message.getst("program"));
        String[] _args = message.get("args");
        String[] args;
        
        if (_args == null) {
            args = new String[1];
        } else {
            args = new String[_args.length + 1];
            for (int i = 0; i < _args.length; i++)
                args[i + 1] = _args[i];
        }
        
        args[0] = program;
        process = Runtime.getRuntime().exec(args);
        inputreader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        errorreader = new BufferedReader(
                new InputStreamReader(process.getErrorStream()));
        output = new Object[3];
        
        output[Iocaste.OUT_PRINT] = getOutput(inputreader);
        output[Iocaste.ERR_PRINT] = getOutput(errorreader);
        process.waitFor();
        
        output[Iocaste.ERR_CODE] = process.exitValue();
        return output;
    }

}
