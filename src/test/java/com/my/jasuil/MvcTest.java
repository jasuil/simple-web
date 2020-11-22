package com.my.jasuil;

import com.my.jasuil.controller.MyController;
import com.my.jasuil.service.MyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyController.class)
public class MvcTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MyService myService;

    @Test
    public void hiTest() throws Exception {
        Mockito.when(myService.hi(Arrays.asList(1,4))).thenReturn(Arrays.asList(1,4));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hi?idList=1,4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        JsonParser jsonParser = new JacksonJsonParser();
        List<Integer> list = (List) jsonParser.parseMap(result.getResponse().getContentAsString()).get("idList");
        Assertions.assertTrue(list.contains(1) && list.contains(4));
    }
}
