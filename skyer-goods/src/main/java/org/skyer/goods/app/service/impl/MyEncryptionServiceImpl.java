package org.skyer.goods.app.service.impl;

import org.skyer.goods.app.service.MyEncryptionService;
import org.skyer.starter.keyencrypt.core.EncryptProperties;
import org.skyer.starter.keyencrypt.core.EncryptionService;
import org.skyer.starter.keyencrypt.core.IEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyEncryptionServiceImpl implements MyEncryptionService {
    @Autowired
    private EncryptProperties encryptProperties;

    private IEncryptionService encryptionService = null;

    private IEncryptionService getEncryptionService() {
        if (encryptionService == null) {
            encryptionService = new EncryptionService(encryptProperties);
        }
        return encryptionService;
    }

    @Override
    public String encrypt(String text) {
        return getEncryptionService().encrypt(text, "");
    }

    @Override
    public String encrypt(Long id) {
        return encrypt(String.valueOf(id));
    }

    @Override
    public String encrypt(Integer id) {
        return encrypt(String.valueOf(id));
    }
}
