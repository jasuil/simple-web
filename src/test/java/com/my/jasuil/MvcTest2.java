package com.my.jasuil;

import com.my.jasuil.service.MyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName(value="ddd")
public class MvcTest2 {

    @Autowired
    private MockMvc mockMvc;
    @MockBean(name = "MockmyService")// avoid to mock injection in  hiIntegrationTest
    MyService MockmyService;

    @Test
    @DisplayName(value="with service mocking")
    public void hiTest1() throws Exception {
        BDDMockito.given(MockmyService.hi(Arrays.asList(1,4))).willReturn(Arrays.asList(1,4));
        this.mockMvc.perform(get("/hi?idList=1,4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1,4")));
    }

    @Test
    @DisplayName(value="integration test")
    public void hiIntegrationTest() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/hi?idList=1,4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        JsonParser jsonParser = new JacksonJsonParser();
        List<Integer> list = (List) jsonParser.parseMap(result.getResponse().getContentAsString()).get("idList");
        Assertions.assertTrue(list.contains(1) && list.contains(4));
    }
}