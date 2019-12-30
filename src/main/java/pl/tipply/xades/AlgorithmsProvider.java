package pl.tipply.xades;

import org.apache.xml.security.algorithms.MessageDigestAlgorithm;
import org.apache.xml.security.signature.XMLSignature;
import xades4j.UnsupportedAlgorithmException;
import xades4j.algorithms.Algorithm;
import xades4j.algorithms.ExclusiveCanonicalXMLWithoutComments;
import xades4j.algorithms.GenericAlgorithm;
import xades4j.providers.AlgorithmsProviderEx;

import java.util.HashMap;
import java.util.Map;

final public class AlgorithmsProvider implements AlgorithmsProviderEx {
    private static final Map<String, Algorithm> signatureAlgsMaps;

    static {
        signatureAlgsMaps = new HashMap<String, Algorithm>(2);
        signatureAlgsMaps.put("DSA", new GenericAlgorithm(XMLSignature.ALGO_ID_SIGNATURE_DSA));
        signatureAlgsMaps.put("RSA", new GenericAlgorithm(XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1));
    }

    public Algorithm getSignatureAlgorithm(String keyAlgorithmName) throws UnsupportedAlgorithmException {
        Algorithm sigAlg = signatureAlgsMaps.get(keyAlgorithmName);

        if (null == sigAlg) {
            throw new UnsupportedAlgorithmException("Signature algorithm not supported by the provider", keyAlgorithmName);
        }

        return sigAlg;
    }

    public Algorithm getCanonicalizationAlgorithmForSignature() {
        return new ExclusiveCanonicalXMLWithoutComments();
    }

    public Algorithm getCanonicalizationAlgorithmForTimeStampProperties() {
        return new ExclusiveCanonicalXMLWithoutComments();
    }

    public String getDigestAlgorithmForDataObjsReferences() {
        return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;
    }

    public String getDigestAlgorithmForReferenceProperties() {
        return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;
    }

    public String getDigestAlgorithmForTimeStampProperties() {
        return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;
    }
}