package io.calidog.certstream;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.time.Instant;
import java.util.*;

/**
 * Some methods that might be useful for accessing certificate
 * data. Not a complete implementation of x509Certificate.
 */
public class CertStreamCertificate extends X509Certificate {
    private HashMap<String, String> subject;
    private HashMap<String, String> extensions;

    private double notBefore;
    private double notAfter;

    private String asDer;

    private CertStreamCertificate() {}

    public static Certificate fromPOJO(CertStreamCertificatePOJO pojo) throws CertificateException {
        CertStreamCertificate fullCertificate = new CertStreamCertificate();

        if (pojo.asDer.isEmpty())
        {
            return null;
        }

        fullCertificate.asDer = pojo.asDer;

        fullCertificate.extensions = pojo.extensions;

        fullCertificate.notAfter = pojo.notAfter;
        fullCertificate.notBefore = pojo.notBefore;

        fullCertificate.subject = pojo.subject;

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

    /**Not implemented*/
    @Override
    public int getVersion() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public BigInteger getSerialNumber() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public Principal getIssuerDN() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public X500Principal getIssuerX500Principal() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public Principal getSubjectDN() {
        return () -> subject.get("DN");
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        return new X500Principal("", subject);
    }

    @Override
    public Date getNotBefore() {
        return new Date((long)notBefore);
    }

    @Override
    public Date getNotAfter() {
        return new Date((long)notAfter);
    }

    /**Not implemented*/
    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        throw new NotImplementedException();
    }
    /**Not implemented*/
    @Override
    public byte[] getSignature() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public String getSigAlgName() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public String getSigAlgOID() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public byte[] getSigAlgParams() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public boolean[] getIssuerUniqueID() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public boolean[] getSubjectUniqueID() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public boolean[] getKeyUsage() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public int getBasicConstraints() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        throw new NotImplementedException();
    }

    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return super.getIssuerAlternativeNames();
    }

    @Override
    public void verify(PublicKey publicKey, Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        super.verify(publicKey, provider);
    }

    private byte[] memoizedEncodedCert = null;
    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        if (memoizedEncodedCert == null) {
            memoizedEncodedCert = Base64.getDecoder().decode(asDer);
        }
        return Arrays.copyOf(memoizedEncodedCert, memoizedEncodedCert.length);
    }

    /**Not implemented*/
    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public void verify(PublicKey publicKey, String s) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public String toString() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public PublicKey getPublicKey() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public boolean hasUnsupportedCriticalExtension() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public Set<String> getCriticalExtensionOIDs() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        throw new NotImplementedException();
    }

    /**Not implemented*/
    @Override
    public byte[] getExtensionValue(String s) {
        throw new NotImplementedException();
    }

    /**
     * Gets the String (Whatever String we get from CertStream)
     * for the extension value (extnValue) identified by the
     * passed-in oid String. The oid string is represented
     * by whatever CertStream passes us.
     */
    public String getStringExtensionValue(String key)
    {
        return extensions.get(key);
    }
}
