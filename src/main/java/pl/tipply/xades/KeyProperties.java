package pl.tipply.xades;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("key")
public class KeyProperties {
    private String p12Filepath;
    private String p12Password;
    private String password;

    public String getP12Filepath() {
        return p12Filepath;
    }

    public void setP12Filepath(String p12Filepath) {
        this.p12Filepath = p12Filepath;
    }

    public String getP12Password() {
        return p12Password;
    }

    public void setP12Password(String p12Password) {
        this.p12Password = p12Password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
