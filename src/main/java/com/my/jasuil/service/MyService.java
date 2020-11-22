package com.my.jasuil.service;

import com.my.jasuil.data.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {
    public List<Integer> hi(List<Integer> idList){
        return idList;
    }
}
