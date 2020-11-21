package com.my.jasuil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UnitTest {

    @InjectMocks
    MyController myController;
    @Mock
    MyService myService;

    @Test
    @Description("hi test")
    public void hiTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(myController).build();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        BDDMockito.given(myService.hi(list)).willReturn(Arrays.asList(1,4));

        MvcResult result = mockMvc.perform(get("/hi?idList=1,4"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertTrue(result.getResponse().getContentAsString().contains("1,4"));

    }
}
