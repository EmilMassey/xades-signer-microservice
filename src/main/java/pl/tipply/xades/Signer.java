package pl.tipply.xades;

import eu.europa.esig.dss.DomUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import xades4j.XAdES4jException;
import xades4j.production.*;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import xades4j.utils.XadesProfileResolutionException;

import java.security.KeyStoreException;

@Service
final public class Signer {
    private final KeyProperties keyProperties;

    public Signer(KeyProperties keyProperties) {
        this.keyProperties = keyProperties;
    }

    @NotNull Document sign(String xmlString) throws XAdES4jException, KeyStoreException {
        return this.sign(DomUtils.buildDOM(xmlString), createDocument());
    }

    @NotNull Document sign(String xmlString, String rootElementTagName) throws XAdES4jException, KeyStoreException {
        return this.sign(DomUtils.buildDOM(xmlString), createDocument(rootElementTagName));
    }

    @NotNull
    Document sign(Document toSign) throws XAdES4jException, KeyStoreException {
        return this.sign(toSign, createDocument());
    }

    @NotNull
    Document sign(Document toSign, String rootElementTagName) throws XAdES4jException, KeyStoreException {
        return this.sign(toSign, createDocument(rootElementTagName));
    }

    @NotNull
    private Document sign(Document toSign, Document targetDocument) throws XAdES4jException, KeyStoreException {
        Node objectContent = targetDocument.importNode(toSign.getDocumentElement(), true);
        Element elemToSign = targetDocument.getDocumentElement();

        DataObjectDesc obj1 = new EnvelopedXmlObject(objectContent);
        SignedDataObjects dataObjs = new SignedDataObjects(obj1);

        this.getXadesSigner().sign(dataObjs, elemToSign);

        return targetDocument;
    }

    private XadesSigner getXadesSigner() throws KeyStoreException, XadesProfileResolutionException {
        KeyingDataProvider keyProvider = new FileSystemKeyStoreKeyingDataProvider(
                "pkcs12",
                this.keyProperties.getP12Filepath(),
                new FirstCertificateSelector(),
                new DirectPasswordProvider(this.keyProperties.getP12Password()),
                new DirectPasswordProvider(this.keyProperties.getPassword()),
                true
        );

        XadesSigningProfile profile = new XadesBesSigningProfile(keyProvider).withAlgorithmsProviderEx(new AlgorithmsProvider());

        return profile.newSigner();
    }

    @NotNull
    private static Document createDocument() {
        Document document = DomUtils.buildDOM();
        Element element = document.createElement("root");
        document.appendChild(element);

        return document;
    }

    @NotNull
    private static Document createDocument(String rootElementTagName) {
        Document document = DomUtils.buildDOM();
        Element element = document.createElement(rootElementTagName);
        document.appendChild(element);

        return document;
    }
}
