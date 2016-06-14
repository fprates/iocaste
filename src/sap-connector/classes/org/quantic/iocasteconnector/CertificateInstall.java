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

public class CertificateInstall {
    
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
    
    private static final InputStream getStream(String path) throws Exception {
        ByteArrayInputStream bais;
        FileInputStream fis = new FileInputStream(path);
        DataInputStream dis = new DataInputStream(fis);
        byte[] bytes = new byte[dis.available()];
        
        dis.readFully(bytes);
        bais = new ByteArrayInputStream(bytes);
        dis.close();
        return bais;
    }

    public static void run(String alias, String path, String certname,
            char[] password) throws Exception {
        String javahome;
        File ksfile;
        KeyStore keystore;
        FileInputStream in;
        FileOutputStream out;
        Certificate certificate;
        CertificateFactory factory;
        InputStream certificatestream;
        
        javahome = System.getProperty("java.home");
        keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        ksfile = new File(getKeyStore(javahome));
        in = new FileInputStream(ksfile);
        keystore.load(in, password);
        in.close();

        if (keystore.containsAlias(alias)) {
            System.out.println("cerificate already installed.");
            return;
        }
        
        certificatestream = getStream(getCertificatePath(path, certname));
        factory = CertificateFactory.getInstance("X.509");
        certificate = factory.generateCertificate(certificatestream);
        keystore.setCertificateEntry(alias, certificate);
        
        out = new FileOutputStream(ksfile);
        keystore.store(out, password);
        out.close();
        System.out.println("cerificate installed.");
    }
}
