package org.skyer.channel.infra.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HPFM Properties
 *
 * @author
 */
@ConfigurationProperties(prefix = ChannelProperties.PREFIX)
public class ChannelProperties {

    public static final String PREFIX = "skyer.platform";
    private static final String HTTP_PROTOCOL_SUFFIX = "://";

    /**
     * 启动时是否刷新缓存,默认true
     */
    private Boolean initCache = true;
    /**
     * 平台Http协议,默认http
     */
    private String httpProtocol = "http";
    /**
     * 完整平台Http协议,默认http://
     */
    private String fullHttpProtocol = "http://";

    /**
     * @return 启动时是否刷新缓存,默认true
     */
    public Boolean getInitCache() {
        return initCache;
    }

    /**
     * 获取卡片使用的模板角色Code
     */
    private List<String> roleTemplateCodes;

    private Encrypt encrypt = new Encrypt();

    public List<String> getRoleTemplateCodes() {
        return roleTemplateCodes;
    }

    public void setRoleTemplateCodes(List<String> roleTemplateCodes) {
        this.roleTemplateCodes = roleTemplateCodes;
    }

    public void setInitCache(Boolean initCache) {
        this.initCache = initCache;
    }

    /**
     * @return 平台Http协议,默认http
     */
    public String getHttpProtocol() {
        return httpProtocol;
    }

    public void setHttpProtocol(String httpProtocol) {
        this.httpProtocol = httpProtocol;
        this.fullHttpProtocol = httpProtocol + HTTP_PROTOCOL_SUFFIX;
    }

    /**
     * @return 完整平台Http协议,默认http://
     */
    public String getFullHttpProtocol() {
        return fullHttpProtocol;
    }

    public Encrypt getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Encrypt encrypt) {
        this.encrypt = encrypt;
    }

    /**
     * 加密私钥和公钥
     */
    public static class Encrypt {
        /**
         * 密码加密公钥
         */
        private String publicKey =
                        "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIPdTZ1b9Q13Y2YmZ4mjQnFpypCqhV6rjGgsTQMYqC0eVu/je3/L98/4/j3lNePWqwHgCDD0mxISFm44nDKfPbcCAwEAAQ==";
        /**
         * 密码加密私钥
         */
        private String privateKey =
                        "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAg91NnVv1DXdjZiZniaNCcWnKkKqFXquMaCxNAxioLR5W7+N7f8v3z/j+PeU149arAeAIMPSbEhIWbjicMp89twIDAQABAkAYAgbLi56GISJ7yqo3KP5D39r9k85MejdKECbFVYvpCsLG51IrwtVcdGJTExmkzIuOWnwqRAYUjpQnVvPvLzZhAiEA/Dyk4j+XZtC7NVxrXjn+PHG0vWZ8x14G5sBL6RfgzEsCIQCF1O0383YF0BbDUGkBoaVGmEc3H0D7FQF5L4gMe5EYxQIgLljOSyw4G5I68+EZcWTijCSweyS7xL5IMlofGKNkkOUCIC+hqdAZEW9eMUvQaZ8QBf/LIMWIlRsHzd9wwE4ZCHo1AiEAqsUlDIIfPOrjQfbeYVWSD+Ac9PzMiFfH9H/WMuaWRtw=";


        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

    }

}
