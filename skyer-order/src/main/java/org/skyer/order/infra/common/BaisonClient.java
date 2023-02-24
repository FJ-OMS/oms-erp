package org.skyer.order.infra.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.skyer.order.infra.enums.BaiSonServiceTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BaisonClient {
    @Value("${baison.url}")
    private String url;
    @Value("${baison.key}")
    private String key;
    @Value("${baison.secret}")
    private String secret;
    @Value("${baison.param}")
    private String param;
    @Value("${baison.request-time-format}")
    private String requestTimeFormat;
    @Value("${baison.version}")
    private String version;

    //返回调用成功关键字
    public static final String STATUS = "api-success";

    private static final String KEY = "key";

    private static final String REQUEST_TIME = "requestTime";

    private static final String SECRET = "secret";

    private static final String VERSION = "version";

    private static final String SERVICE_TYPE = "serviceType";

    private static final String DATA_NAME = "data";

    private static final String SIGN = "sign";

    //定义一个Http客户端超时请求和监听
    private final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)//设置连接超时时间
            .readTimeout(15, TimeUnit.SECONDS)//设置读取超时时间
            .addInterceptor(new LogInterceptor())
            .build();

    /**
     * 发送百胜发送POST请求FORM表单
     *
     * @param serviceType
     * @param data
     * @return
     */
    public String sendPost(BaiSonServiceTypeEnum serviceType, String data) {
        String dateFormat = DateUtil.format(new Date(), requestTimeFormat);
        String format = String.format(param, key, dateFormat, secret, serviceType.getTypeName(), data);
        String md5 = DigestUtil.md5Hex(format);
        RequestBody requestBody = new FormBody.Builder()
                .add(KEY, key)
                .add(REQUEST_TIME, dateFormat)
                .add(SECRET, secret)
                .add(VERSION, version)
                .add(SERVICE_TYPE, serviceType.getTypeName())
                .add(DATA_NAME, data)
                .add(SIGN, md5).build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        String ret = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            ret = response.body().string();
        } catch (IOException e) {
            log.error("bai-son send post 异常", e);
        }
        return ret;
    }

}
