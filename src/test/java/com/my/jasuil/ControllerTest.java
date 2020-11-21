package com.my.jasuil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void hiTest() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", "tv=samsung;app_auth=jasuil;phone=samsung;" );
        ResponseEntity<String> result = this.restTemplate.exchange("http://localhost:" + port + "/hi?idList=1,4",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                String.class);
        JsonParser jsonParser = new JacksonJsonParser();
        List<Integer> list = (List) jsonParser.parseMap(result.getBody()).get("idList");
        Assertions.assertTrue(list.contains(1) && list.contains(4));

    }

}
