package ci.patrickbeugre.spring.base.business;

import org.apache.commons.io.FileUtils;
import org.shredzone.acme4j.Certificate;
import org.shredzone.acme4j.*;
import org.shredzone.acme4j.challenge.Http01Challenge;
import org.shredzone.acme4j.exception.AcmeException;
import org.shredzone.acme4j.util.CSRBuilder;
import org.shredzone.acme4j.util.KeyPairUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Service
public class AcmeBusiness {

    private final static DefaultResourceLoader loader = new DefaultResourceLoader();

    private Logger slf4jLogger = LoggerFactory.getLogger(getClass());




    /**
     * Genereate hote certificate
     *      request for performing action
     *      locale to be used
     * @return
     *      the response with status, fullchain and key
     */
    public void generateCert(String domain) throws Exception {
        String fullChain = null;
        String asciiPrivateKey = null;
        Session session = new Session("");
        KeyPair keyPair = this.getKeyPair();
        if (domain == null) {
            throw new RuntimeException("initialHost is required.");
        }
        if (keyPair == null) {
            throw new RuntimeException("keypair generation failed.");
        }
        //une seule clé pour tous mes comptes
        Account account = this.accountExists(keyPair, session);
        if (account == null) {
            //account don't exist
            account = this.createAccount(keyPair, session);
        }
        if (account == null) {
            throw new RuntimeException("account creation failed" + domain);
        }
        slf4jLogger.info("begin accountLocationUrl");
        URL accountLocationUrl = account.getLocation();
        Login login = session.login(accountLocationUrl, keyPair);
        Account accountLogin = login.getAccount();
        Order order = accountLogin.newOrder()
                .domains(domain)
                //.notAfter(Instant.now().plus(Duration.ofDays(20L)))
                .create();
        slf4jLogger.info("begin request for domain " + domain);
        Pair<Certificate, KeyPair> pairOf = null;
        Certificate certificate = null;
        KeyPair domainKeyPair = null;
        slf4jLogger.info("before authorizations ");
        order.getAuthorizations().forEach(System.out::println);
        for (Authorization auth : order.getAuthorizations()) {
            if (auth.getStatus() != Status.VALID) {
                pairOf = this.processAuth(auth, order, domain);
            }
        }
        slf4jLogger.info("after authorizations ");
        if (pairOf == null || pairOf.getFirst() == null) {
            throw new RuntimeException("cannot finalized challenge");
        }
        certificate = pairOf.getFirst();
        domainKeyPair = pairOf.getSecond();
        X509Certificate x509Certificate = certificate.getCertificate();
        //List<X509Certificate> certificateList = certificate.getCertificateChain();
        String fileName = "/tmp/cert-chain.crt";
        try (FileWriter fw = new FileWriter(fileName)) {
            certificate.writeCertificate(fw);//write chain
        }

        File file = new File("/tmp/cert-chain.crt");
        fullChain = FileUtils.readFileToString(file, "UTF-8");
        asciiPrivateKey = this.getKeys(domainKeyPair.getPrivate());
        //ORDER CODE TO RETURN GOOD RESPONSE

    }



    /**
     * Get private key into string
     *
     * @param privateKey
     *      request for performing action
     * @return
     *      the string representation of private key
     */
    public String getKeys(final PrivateKey privateKey) {
        Base64.Encoder encoder = Base64.getEncoder();
        String asciiPrivateKey = null;
        File file = null;
        try {
            String outFile = "/tmp/";
            file = new File(outFile + "private.key");
            Writer out = new FileWriter(file);
            out.write("-----BEGIN PRIVATE KEY-----\n");
            out.write(encoder.encodeToString(privateKey.getEncoded()));
            out.write("\n-----END PRIVATE KEY-----\n");
            out.close();
            asciiPrivateKey = FileUtils.readFileToString(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return asciiPrivateKey;
    }

    /**
     * Get account keypair to perform login to acme server
     * @return
     *      keypair
     */
    private KeyPair getKeyPair() {
        KeyPair userKeyPair = null;
        try {
            //GET User KeyPair into database or insert into for the first time

            /*AccountKeyPair accountKeyPair = null;
            accountKeyPair = accountKeyPairRepository.findByEnv(paramsUtils.getAccountEnv());
            if (accountKeyPair != null) {
                userKeyPair = this.getKeyPairFromDB(accountKeyPair.getPrivateKey(), accountKeyPair.getPublicKey());

            } else {
                userKeyPair = KeyPairUtils.createKeyPair(2048);
                accountKeyPair = new AccountKeyPair();
                accountKeyPair.setPrivateKey(userKeyPair.getPrivate().getEncoded());
                accountKeyPair.setPublicKey(userKeyPair.getPublic().getEncoded());
                accountKeyPair.setEnv(paramsUtils.getAccountEnv());
                accountKeyPairRepository.save(accountKeyPair);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userKeyPair;
    }


    /**
     * Check existing account to acme
     *
     * @param keyPair
     *      account keyPair
     * @param session
     *      session to establish connection
     * @return
         *      the account object
     */
    private Account accountExists(final KeyPair keyPair, final Session session) {
        Account account = null;
        try {
            account = new AccountBuilder()
                    .onlyExisting()         // Do not create a new account
                    .useKeyPair(keyPair)
                    .create(session);
            slf4jLogger.info("Account exists");
        } catch (Exception e) {
            slf4jLogger.info("Account does not exists");
            e.printStackTrace();
        }
        return account;
    }

    /**
     * Create account to acme
     *
     * @param keyPair
     *      account keyPair
     * @param session
     *      session to establish connection
     * @return
     *      the account object
     */
    private Account createAccount(final KeyPair keyPair, final Session session) {
        Account account = null;
        try {
            account = new AccountBuilder()
                    .addContact("pt.beugre@gmail.com")
                    .agreeToTermsOfService()
                    .useKeyPair(keyPair)
                    .create(session);
        } catch (Exception e) {
            slf4jLogger.info("Account creation failed");
            e.printStackTrace();

        }
        return account;
    }

    /**
     * Process challenge to get certificate
     *
     * @param auth
     *      auth object
     * @param order
     *      order object
     * @param domain
     *      domain object
     * @return
     *      the pair of certificate and domain keypair
     */
    private Pair<Certificate, KeyPair> processAuth(final Authorization auth, final Order order, final String domain) throws Exception {
        slf4jLogger.info("begin token and content for challenge ");
        Http01Challenge challenge = auth.findChallenge(Http01Challenge.TYPE);
        Certificate certificate = null;
        KeyPair domainKeyPair = null;
        String token = challenge.getToken();
        String content = challenge.getAuthorization();

        //save key and token into redis and get automatically service to challenge
/*       boolean acmeResult = redisValue.save(token, content);
        if (!acmeResult) {
            throw new RuntimeException("save for token failed.");
        }*/
        slf4jLogger.info("after token and content for challenge ");
        challenge.trigger();
        int attempts = 5;
        while (auth.getStatus() != Status.VALID && attempts-- > 0) {
            Thread.sleep(3000L);
            auth.update();
        }
        slf4jLogger.info("challenge okay");
        domainKeyPair = KeyPairUtils.createKeyPair(2048);

        certificate = this.getCert(order, domainKeyPair, domain);
        if (certificate == null) {
            throw new RuntimeException("Cert generation failed.");
        }
        return Pair.of(certificate, domainKeyPair);
    }


    /**
     * Process challenge to get certificate when renew
     *
     * @param order
     *      order object
     * @param domainKeyPair
     *      domain keypair
     * @param domainKeyPair
     *      domainKeyPair object
     * @return
     *      the certificate value
     */
    private Certificate getCert(final Order order, final KeyPair domainKeyPair, final String domain) throws Exception {
        slf4jLogger.info("before sign for domain");
        Certificate cert = null;
        String csrValue = null;
        CSRBuilder csrb = new CSRBuilder();
        csrb.addDomain(domain);
        csrb.setOrganization(domain);
        csrb.setCountry(domain);
        csrb.setState(domain);
        csrb.setOrganizationalUnit(domain);
        csrb.setLocality(domain);
        csrb.sign(domainKeyPair);
        byte[] csr = csrb.getEncoded();

        //save csr and key before getting certificate
        String fileName = "/tmp/cert-chain.csr";
        try (FileWriter fw = new FileWriter(fileName)) {
            csrb.write(fw);
        }
        csrValue = FileUtils.readFileToString(new File(fileName), "UTF-8");


        order.execute(csr);
        int attempts = 10;
        while (order.getStatus() != Status.VALID && attempts-- > 0) {
            Thread.sleep(3000L);
            order.update();
        }
        if (order.getCertificate() == null) {
            throw new RuntimeException("Cert generation failed.");
        }
        cert = order.getCertificate();
        slf4jLogger.info("after sign for domain");
        return cert;

    }


    public final boolean revokeWithSession(final KeyPair domainKeyPair, final X509Certificate cert) {
        Session session = new Session("");
        try {
            Certificate.revoke(session, domainKeyPair, cert, RevocationReason.REMOVE_FROM_CRL);
            return true;
        } catch (AcmeException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Revoke certificate with login object
     *
     * @param login
     *      object login
     * @param cert
     *      X509Certificate object
     * @return
     *      true or false
     *
     */
    private boolean revokeWithlogin(final Login login, X509Certificate cert) {
        try {
            Certificate.revoke(login, cert, RevocationReason.REMOVE_FROM_CRL);
            return true;
        } catch (AcmeException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Load certificate from database
     *
     * @param fileName
     *     fileName
     *
     * @return
     *      X509Certificate value
     *
     */
    public final X509Certificate loadCertificate(final File fileName) throws CertificateException, IOException {
        X509Certificate cert = null;
        CertificateFactory certFactory = CertificateFactory.getInstance("X509");
        FileInputStream in = new FileInputStream(fileName);
        try {
            cert = (X509Certificate) certFactory.generateCertificate(in);
        } catch (java.security.cert.CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cert;
    }
    /**
     * Check certificate expiration date
     *
     * @param cert
     *     X509Certificate value
     *
     * @return
     *      true of false
     *
     */
    public final boolean isValid(final X509Certificate cert) {
        try {
            cert.checkValidity();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get certificate with private and public keys
     *
     * @param privateKeyBytes
     *     byte[] of private key
     * @param publicKeyBytes
     *    byte[] of public key
     *
     * @return
     *      keypair
     *
     */
    public final KeyPair getKeyPairFromDB(final byte[] privateKeyBytes, final byte[] publicKeyBytes) throws NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
        KeyPair domainKeyPair = null;
        try {
            PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            domainKeyPair = new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return domainKeyPair;

    }

}