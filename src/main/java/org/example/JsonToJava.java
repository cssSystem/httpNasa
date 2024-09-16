package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public class JsonToJava {

    public static Nasa toJavaList(CloseableHttpResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                response.getEntity().getContent(), Nasa.class
        );
    }
}