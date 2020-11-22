package com.my.jasuil.service;

import com.my.jasuil.data.entities.UserInfo;
import com.my.jasuil.data.repository.UserInfoRepository;
import org.jooq.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    public UserInfo findById(Integer id) {
        return userInfoRepository.findById(id).orElse(null);
    }
    public UserInfo findByEmail(String email) {
        return userInfoRepository.findByEmail(email);
    }

    public void save(UserInfo userInfo){
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfoRepository.save(userInfo);
    }
    public void deleteByEmail(String email) {
        userInfoRepository.deleteByEmail(email);
    }

}
