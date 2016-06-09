package org.quantic.iocasteconnector;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.iocaste.external.common.External;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.server.JCoServerContext;

public class CertificateInstall extends AbstractSAPFunctionHandler {
    public static final String CERT_ALIAS = "iocaste";
    
    public CertificateInstall(External external) {
        super(external, DONT_CONNECT);
    }
    
    public static final String getCertificatePath(String path, String certnm) {
        return (path == null)? certnm : new StringBuilder(path).
                append(File.separator).append(certnm).toString();
    }
    
    public static final String getKeyStore(String javahome) {
        return new StringBuilder(javahome).append(File.separator).
                append("lib").append(File.separator).
                append("security").append(File.separator).
                append("cacerts").toString();
    }
    
    private final InputStream getStream(String path) throws Exception {
        ByteArrayInputStream bais;
        FileInputStream fis = new FileInputStream(path);
        DataInputStream dis = new DataInputStream(fis);
        byte[] bytes = new byte[dis.available()];
        
        dis.readFully(bytes);
        bais = new ByteArrayInputStream(bytes);
        dis.close();
        return bais;
    }

    @Override
    public void run(JCoServerContext context, JCoFunction function)
            throws Exception {
        JCoParameterList parameters;
        String secret, path, javahome, certname;
        File ksfile;
        KeyStore keystore;
        FileInputStream in;
        FileOutputStream out;
        Certificate certificate;
        CertificateFactory factory;
        InputStream certificatestream;
        char[] password;
        
        parameters = function.getImportParameterList();
        secret = parameters.getString("SECRET");
        path = parameters.getString("PATH");
        certname = parameters.getString("CERT_NAME");
        
        javahome = System.getProperty("java.home");
        password = secret.toCharArray();
        keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        ksfile = new File(getKeyStore(javahome));
        in = new FileInputStream(ksfile);
        keystore.load(in, password);
        in.close();

        certificatestream = getStream(getCertificatePath(path, certname));
        factory = CertificateFactory.getInstance("X.509");
        certificate = factory.generateCertificate(certificatestream);
        keystore.setCertificateEntry(CERT_ALIAS, certificate);
        
        out = new FileOutputStream(ksfile);
        keystore.store(out, password);
        out.close();
    }
}
