package com.chrisyoung.demo.service;

import com.chrisyoung.demo.domain.UserAuths;

/**
 * @program: tokendemo
 * @author: Chris Young
 * @create: 2018-11-29 09:02
 * @description:
 **/

public interface AuthService {
    int register(UserAuths newAuth);
    String login(String identify,String credential);
    String refresh(String oldToken);
}
