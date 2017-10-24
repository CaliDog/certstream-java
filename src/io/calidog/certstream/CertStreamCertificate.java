package io.calidog.certstream;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class CertStreamCertificate extends X509Certificate {
    HashMap<String, String> subject;
    HashMap<String, String> extensions;

    double notBefore;
    double notAfter;

    String asDer;

    private CertStreamCertificate() {}

    public static Certificate fromPOJO(CertStreamCertificatePOJO pojo) throws CertificateException {
        CertStreamCertificate fullCertificate = new CertStreamCertificate();

        if (pojo.asDer.isEmpty())
        {
            return null;
        }
        else
        {
            //todo remove stupid dev stuff
            System.out.println("asDer is : " + pojo.asDer);
        }

        fullCertificate.asDer = pojo.asDer;

        //paranoid wrapping
        fullCertificate.extensions= new HashMap<>(pojo.extensions.size());
        fullCertificate.extensions.putAll(pojo.extensions);

        fullCertificate.notAfter = pojo.notAfter;
        fullCertificate.notBefore = pojo.notBefore;

        //more paranoia
        fullCertificate.subject = new HashMap<>(pojo.subject.size());
        fullCertificate.subject.putAll(pojo.subject);

        return fullCertificate;
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        checkValidity(Date.from(Instant.now()));
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        if (date.before(getNotBefore()))
        {
            throw new CertificateNotYetValidException();
        }
        if (date.after(getNotAfter()))
        {
            throw new CertificateExpiredException();
        }
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public BigInteger getSerialNumber() {
        return null;
    }

    @Override
    public Principal getIssuerDN() {
        throw new NotImplementedException();
    }

    @Override
    public Principal getSubjectDN() {
        return () -> subject.get("DN");
    }

    @Override
    public Date getNotBefore() {
        return new Date((long)notBefore);
    }

    @Override
    public Date getNotAfter() {
        return new Date((long)notAfter);
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        throw new NotImplementedException();
    }

    @Override
    public byte[] getSignature() {
        throw new NotImplementedException();
    }

    @Override
    public String getSigAlgName() {
        throw new NotImplementedException();
    }

    @Override
    public String getSigAlgOID() {
        throw new NotImplementedException();
    }

    @Override
    public byte[] getSigAlgParams() {
        throw new NotImplementedException();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        throw new NotImplementedException();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        throw new NotImplementedException();
    }

    @Override
    public boolean[] getKeyUsage() {
        throw new NotImplementedException();
    }

    @Override
    public int getBasicConstraints() {
        throw new NotImplementedException();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return Base64.getDecoder().decode(asDer);
    }

    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        throw new NotImplementedException();
    }

    @Override
    public void verify(PublicKey publicKey, String s) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        throw new NotImplementedException();

    }

    @Override
    public String toString() {
        throw new NotImplementedException();
    }

    @Override
    public PublicKey getPublicKey() {
        throw new NotImplementedException();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        throw new NotImplementedException();
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        throw new NotImplementedException();
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        throw new NotImplementedException();
    }

    @Override
    public byte[] getExtensionValue(String s) {
        throw new NotImplementedException();
    }
}
