package com.chrisyoung.demo.service;

import com.chrisyoung.demo.domain.UserAuths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl authService;

    @Test
    public void register() {
      //  String SECRET=UUID.randomUUID().toString().replace("-","a")+UUID.randomUUID().toString().replace("-","a")+UUID.randomUUID().toString().replace("-","a");
        //String r=SECRET;
        UserAuths auths=new UserAuths();
        auths.setUId(UUID.randomUUID().toString().replace("-",""));
        auths.setIdentify("1234567");
        auths.setCredential("1234567");
        auths.setRole("ADMIN");
        authService.register(auths);
    }

    @Test
    public void login() {
    }
}