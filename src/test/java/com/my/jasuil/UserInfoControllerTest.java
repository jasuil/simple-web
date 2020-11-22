package com.my.jasuil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.my.jasuil.controller.UserInfoController;
import com.my.jasuil.data.entities.UserInfo;
import com.my.jasuil.data.repository.UserInfoRepository;
import com.my.jasuil.service.UserInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserInfoController.class)
public class UserInfoControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserInfoService userInfoService;
    @MockBean
    UserInfoRepository userInfoRepository;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void findByEmail_test() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("jasuil@daum.net");
        userInfo.setName("성일짱");
        userInfo.setPassword("1212");
        userInfo.setCreatedAt(LocalDateTime.now());

        BDDMockito.given(userInfoService.findByEmail("jasuil@daum.net")).willReturn(userInfo);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/userinfo/info/email/jasuil@daum.net"))
                .andDo(print())
                .andReturn();

        JsonParser jsonParser = new JacksonJsonParser();
        Assertions.assertTrue(jsonParser.parseMap(mvcResult.getResponse().getContentAsString()).get("email").equals("jasuil@daum.net"));
    }

    @Test
    public void save_test() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("jasuil@daum.net");
        userInfo.setName("성일짱");
        userInfo.setPassword("1212");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userInfo);

        //BDDMockito.given(userInfoService.findByEmail("jasuil@daum.net")).willReturn(userInfo);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/userinfo/new")
                .contentType(APPLICATION_JSON_UTF8).content(requestJson))
                .andDo(print())
                .andReturn();

        JsonParser jsonParser = new JacksonJsonParser();
        Assertions.assertTrue(mvcResult.getResponse().getStatus() == 202);
    }
}
