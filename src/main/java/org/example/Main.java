package org.example;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    protected static String keyNasa = "AFG6vOwxqFV7KdL3p4X0CvmHDCuhcLJkzpquflL2";
    protected static String nameURL = "https://api.nasa.gov/planetary/apod?api_key=";

    public static void main(String[] args) throws IOException {
        boolean stat = true;
        try (CloseableHttpClient httpClient = httpClient()) {
            HttpGet request = new HttpGet(nameURL + keyNasa);
            CloseableHttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                Nasa nasaObject = JsonToJava.toJavaList(response);
                request = new HttpGet(nasaObject.getUrl());
                response = httpClient.execute(request);
                if (response.getStatusLine().getStatusCode() == 200) {
                    try (FileOutputStream fos = new FileOutputStream(new File(nasaObject.getUrl()).getName())) {
                        fos.write(response.getEntity().getContent().readAllBytes());
                    }
                } else {
                    stat = false;
                }
            } else {
                stat = false;
            }
        }
        if (stat) {
            System.out.println("ok!");
        } else {
            System.out.println("Что-то пошло не так...");
        }
    }

    public static CloseableHttpClient httpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
    }
}

