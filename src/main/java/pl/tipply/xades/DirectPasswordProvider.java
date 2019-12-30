package pl.tipply.xades;

import xades4j.providers.impl.KeyStoreKeyingDataProvider.KeyEntryPasswordProvider;
import xades4j.providers.impl.KeyStoreKeyingDataProvider.KeyStorePasswordProvider;

import java.security.cert.X509Certificate;

final public class DirectPasswordProvider implements KeyStorePasswordProvider, KeyEntryPasswordProvider {
    private char[] password;

    DirectPasswordProvider(String password) {
        this.password = password.toCharArray();
    }

    public char[] getPassword() {
        return password;
    }

    public char[] getPassword(String entryAlias, X509Certificate entryCert) {
        return password;
    }
}