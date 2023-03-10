package com.hotstrip.jbnode.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // 使用HttpComponentsClientHttpRequestFactory来替换默认的HTTP连接实现
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        // 创建一个禁用主机名验证和SSL握手不严格 这个设置将允许连接到不受信任且可能存在风险的主机
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                    .setProtocol("TLSv1.2")
                    .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setSSLContext(sslContext)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);

        // 设置连接超时时间为5秒
        factory.setConnectTimeout(5000);
        // 设置读取超时时间为10秒
        factory.setReadTimeout(10000);

        // 设置HttpClientBuilder
        factory.setHttpClient(httpClientBuilder.build());

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());

        // 通过builder创建RestTemplate实例，并设置RequestFactory
        return builder.requestFactory(() -> factory)
                .messageConverters(messageConverters)
                .build();
    }
}
