package org.skyer.gateway.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * eg.30:/hpfm/sock-js/837/pakj5o2f/websocket:10.211.99.169
 *
 * @author XCXCXCXCX
 * @version 1.2.0
 * @date 2020/1/3 11:02 上午
 */
public class RequestCountUtils {

    private static final String SPLIT = "::";

    private static final Pattern PATTERN = Pattern.compile("[0-9]+:[^:]+:[0-9]+.[0-9]+.[0-9]+.[0-9]+");

    /**
     * id > monitorRuleId uri > request uri ip > request origin ip
     *
     * @param id
     * @param uri
     * @param ip
     * @return
     */
    public static String generateKey(Long id, String uri, String ip) {
        return id + SPLIT + uri + SPLIT + ip;
    }

    public static boolean validateKey(String key) {
        return key != null && PATTERN.matcher(key).matches();
    }

    public static Long resolveId(String key) {
        return validateKey(key) ? Long.parseLong(doResolve(key, 0)) : null;
    }

    public static String resolveUri(String key) {
        return validateKey(key) ? doResolve(key, 1) : null;
    }

    public static String resolveIp(String key) {
        return validateKey(key) ? doResolve(key, 2) : null;
    }

    private static String doResolve(String key, int index) {
        String[] parts = key.split(SPLIT);
        if (parts.length >= index + 1) {
            return parts[index];
        }
        return StringUtils.EMPTY;
    }

}
