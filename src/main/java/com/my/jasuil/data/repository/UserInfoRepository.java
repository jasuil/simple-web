package com.my.jasuil.data.repository;

import com.my.jasuil.data.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    UserInfo findAllByEmailAndPassword(String email, String password);
    UserInfo findByEmail(String email);
    void deleteByEmail(String email);
}
