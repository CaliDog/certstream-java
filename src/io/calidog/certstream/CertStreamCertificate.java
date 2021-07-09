package io.calidog.certstream;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.time.Instant;
import java.util.*;

/**
 * Some methods that might be useful for accessing certificate
 * data. Not a complete implementation of x509Certificate.
 */
public class CertStreamCertificate extends X509Certificate {
    private HashMap<String, String> subject;
    private HashMap<String, String[]> extensions;
    private HashMap<String, String> issuer;

    private double notBefore;
    private double notAfter;

    String sigAlg;

    String serialNumber;

    String fingerprint;

    String[] allDomains;

    private CertStreamCertificate() {}

    public static CertStreamCertificate fromPOJO(CertStreamCertificatePOJO pojo) throws CertificateException {
        CertStreamCertificate fullCertificate = new CertStreamCertificate();

        fullCertificate.issuer = pojo.issuer;

        fullCertificate.sigAlg = pojo.sigAlg;

        fullCertificate.extensions = pojo.extensions;

        fullCertificate.notAfter = pojo.notAfter;
        fullCertificate.notBefore = pojo.notBefore;

        fullCertificate.subject = pojo.subject;

        fullCertificate.serialNumber = pojo.serialNumber;

        fullCertificate.fingerprint = pojo.fingerprint;

        fullCertificate.allDomains = pojo.allDomains;

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
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public BigInteger getSerialNumber() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public Principal getIssuerDN() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public X500Principal getIssuerX500Principal() {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }
    /**Not implemented*/
    @Override
    public byte[] getSignature() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public String getSigAlgName() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public String getSigAlgOID() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public byte[] getSigAlgParams() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public boolean[] getIssuerUniqueID() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public boolean[] getSubjectUniqueID() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public boolean[] getKeyUsage() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public int getBasicConstraints() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return super.getIssuerAlternativeNames();
    }

    @Override
    public void verify(PublicKey publicKey, Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        super.verify(publicKey, provider);
    }

    @Override
    public byte[] getEncoded() {

        return new byte[1];
    }

    /**Not implemented*/
    @Override
    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public void verify(PublicKey publicKey, String s) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public PublicKey getPublicKey() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public boolean hasUnsupportedCriticalExtension() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public Set<String> getCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        throw new UnsupportedOperationException();
    }

    /**Not implemented*/
    @Override
    public byte[] getExtensionValue(String s) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the String (Whatever String we get from CertStream)
     * for the extension value (extnValue) identified by the
     * passed-in oid String. The oid string is represented
     * by whatever CertStream passes us.
     */
    public String[] getStringExtensionValue(String key)
    {
        return extensions.get(key);
    }
}
