package br.araujos.moviesstar.infraestrutura;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class ApiKeyInterceptor implements Interceptor {

    @Value("${apiKey}")
    private String apiKey;

    public ApiKeyInterceptor() {

    }

    public ApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                // Adicione outros parâmetros aqui, se necessário
                .build();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}