package com.my.jasuil.data;

import com.my.jasuil.data.entities.UserInfo;
import com.my.jasuil.data.repository.UserInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class UserInfoRepositoryTest {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Test
    public void modify_updatedAt_test() {
        List<UserInfo> list = new ArrayList<>();

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("jasuil@daum.net");
        userInfo.setName("성일짱");
        userInfo.setPassword("1212");
        list.add(userInfo);

        userInfo = new UserInfo();
        userInfo.setEmail("jasuil@gmail.com");
        userInfo.setName("성일짱2");
        userInfo.setPassword("1234");
        list.add(userInfo);

        userInfoRepository.saveAll(list);

        UserInfo userInfo2 = userInfoRepository.findById(1).get();
        userInfo2.setName("jasuil");
        UserInfo userInfo3 = userInfoRepository.findById(1).get();

        Assertions.assertTrue(list.get(0).getName().equals(userInfo3.getName()));
        Assertions.assertTrue(userInfo2.getName().equals(userInfo3.getName()));
    }

    @Test
    public void save_test() {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("jasuil@daum.net");
        userInfo.setName("성일짱");
        userInfo.setPassword("1212");
        userInfo.setCreatedAt(LocalDateTime.now());

        userInfoRepository.save(userInfo);
        UserInfo userInfo2 = userInfoRepository.findByEmail("jasuil@daum.net");

        Assertions.assertTrue(userInfo.getEmail().equals(userInfo2.getEmail()));
    }
}
