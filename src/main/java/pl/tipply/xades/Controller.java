package pl.tipply.xades;

import eu.europa.esig.dss.DomUtils;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import xades4j.XAdES4jException;

import java.security.KeyStoreException;

@RestController
@RequestMapping(produces = "application/xml", consumes = "application/xml")
final public class Controller {
    private final Signer signer;

    public Controller(Signer signer) {
        this.signer = signer;
    }

    @RequestMapping("/")
    public String homepage() {
        return "Hello!";
    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public String sign(@RequestBody String xmlString, @RequestParam(required = false) String rootElementTagName) throws XAdES4jException, KeyStoreException {
        Document signed;

        if (rootElementTagName == null) {
            signed = this.signer.sign(xmlString);
        } else {
            signed = this.signer.sign(xmlString, rootElementTagName);
        }

        return DomUtils.xmlToString(signed);
    }
}
