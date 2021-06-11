package io.netty.handler.ssl.util;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;

import static io.netty.handler.ssl.util.SelfSignedCertificate.newSelfSignedCertificate;

/**
 * Generates a self-signed certificate using <a href="http://www.bouncycastle.org/">Bouncy Castle</a>.
 */
final class BouncyCastleSelfSignedCertGenerator {

    private static final Provider PROVIDER = new BouncyCastleProvider();

    private BouncyCastleSelfSignedCertGenerator() {
    }

    static String[] generate(String fqdn, KeyPair keypair, SecureRandom random, Date notBefore, Date notAfter, String algorithm) throws Exception {
        PrivateKey key = keypair.getPrivate();

        // Prepare the information required for generating an X.509 certificate.
        X500Name owner = new X500Name("CN=" + fqdn);
        X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(owner, new BigInteger(64, random), notBefore, notAfter, owner, keypair.getPublic());

        ContentSigner signer = new JcaContentSignerBuilder(algorithm.equalsIgnoreCase("EC") ? "SHA256withECDSA" : "SHA256WithRSAEncryption").build(key);
        X509CertificateHolder certHolder = builder.build(signer);
        X509Certificate cert = new JcaX509CertificateConverter().setProvider(PROVIDER).getCertificate(certHolder);
        cert.verify(keypair.getPublic());

        return newSelfSignedCertificate(fqdn, key, cert);
    }
}
