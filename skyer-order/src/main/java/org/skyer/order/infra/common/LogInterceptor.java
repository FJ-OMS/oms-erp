package org.skyer.order.infra.common;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * OkHttp拦截器
 */
@Slf4j
public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Buffer buffer = new Buffer();
        try {
            request.body().writeTo(buffer);
        } catch (IOException e) {
            log.error("okhttp 异常", e);
        }
        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = request.body().contentType();
        if (contentType != null) {
            charset = contentType.charset(StandardCharsets.UTF_8);
        }
        String requestString = buffer.readString(charset);
        buffer.close();
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            System.err.println(e);
        }
        MediaType respContentType = response.body().contentType();
        String resp = null;
        try {
            resp = response.body().string();
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println(request.url());
        System.out.println(response.code());
        System.out.println(requestString);
        return response.newBuilder().body(ResponseBody.create(resp, respContentType)).build();
    }
}
