package com.capitalone.functional.restclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509KeyManager;

import org.junit.Assert;

import com.google.common.collect.Iterables;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class RestClientAdapter {

    private final String host;

    private final Properties properties;

    private final String configPropFilePath;

    public RestClientAdapter() {
        // this.configPropFilePath = System.getProperty("rest.config.propfile");
        this.configPropFilePath = "/Users/sfi375/MyProject/gitCode/cloud/customer-moneymvmnt-trnsfraccts-cloud/customer-moneymvmnt-trnsfraccts-cloud-test/src/test/resources/restConfig.properties";
        this.properties = retrieveProperties(configPropFilePath);
        this.host = properties.getProperty("baseUrl");
    }

    public RestClient getClient() {
        if (host.startsWith("https")) {
            loadSSL();
        }
        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(host).setLogLevel(LogLevel.BASIC).build();
        return restAdapter.create(RestClient.class);
    }

    private void loadSSL() {
        try {
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            };
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Trusting all the certificates
     * 
     * @throws Exception
     */
    private void trustAllHttpsCertificates() throws Exception {

        final String keyStorePath = configPropFilePath.substring(0, configPropFilePath.lastIndexOf("/") + 1)
                + properties.getProperty("keystorepath");

        final String password = properties.getProperty("keystorepass");

        // get keyStore
        KeyStore keyStore = readKeyStore(keyStorePath, password.toCharArray());

        // get KeyManager
        X509KeyManager x509KeyManager = getKeyManager("SunX509", keyStore, password.toCharArray());

        // get KeyManager []
        KeyManager[] keyManagerArray = new KeyManager[1];
        keyManagerArray[0] = x509KeyManager;

        // Create a trust manager that does not validate certificate chains:
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        // javax.net.ssl.TrustManager tm = new miTM();*/
        trustAllCerts[0] = tm;

        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(keyManagerArray, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    /**
     * Verifying all certificates
     * 
     * @author pon950
     */
    public static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    KeyStore readKeyStore(String certPath, char[] password)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        KeyStore keyStore = KeyStore.getInstance("JKS");

        java.io.FileInputStream fis = null;
        try {
            fis = new java.io.FileInputStream(certPath);
            keyStore.load(fis, password);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return keyStore;
    }

    private X509KeyManager getKeyManager(String algorithm, KeyStore keystore, char[] password)
            throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory factory = KeyManagerFactory.getInstance(algorithm);
        factory.init(keystore, password);
        return Iterables.getFirst(Iterables.filter(Arrays.asList(factory.getKeyManagers()), X509KeyManager.class),
                null);
    }

    private Properties retrieveProperties(final String configPropFilePath) {
        final Properties properties = new Properties();
        FileInputStream stream = null;
        try {
            System.out.println("Config Path of CMTA : " + configPropFilePath);
            stream = new FileInputStream(new File(configPropFilePath));
            properties.load(stream);
        } catch (Exception e) {
            Assert.fail("Failed while loading the rest config properties : " + e);
            throw new AssertionError("Failed while loading the rest config properties : " + e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                System.out.println("Failed to close the config file stream");
            }
        }
        return properties;
    }
}
