package org.skyer.goods.app.service;

/**
 * 加密工具(针对字符串,id等进行加密)
 *
 * @author chenzz
 */
public interface MyEncryptionService {

    /**
     * 加密字符串
     *
     * @param text
     * @return
     */
    String encrypt(String text);

    /**
     * 加密id
     *
     * @param id
     * @return
     */
    String encrypt(Long id);

    /**
     * 加密id
     *
     * @param id
     * @return
     */
    String encrypt(Integer id);
}
