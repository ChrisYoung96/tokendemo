package com.chrisyoung.demo.service;

import com.chrisyoung.demo.dao.IAuthDao;
import com.chrisyoung.demo.domain.UserAuths;
import com.chrisyoung.demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @program: tokendemo
 * @author: Chris Young
 * @create: 2018-11-29 09:04
 * @description:
 **/

@Service
public class AuthServiceImpl implements AuthService {


    private JWTUtil jwtUtil;
    private IAuthDao authDao;


    @Autowired
    public AuthServiceImpl(JWTUtil jwtUtil, IAuthDao authDao) {
        this.jwtUtil = jwtUtil;
        this.authDao = authDao;
    }

    @Override
    public int register(UserAuths newAuth) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        newAuth.setCredential(encoder.encode(newAuth.getCredential()));
        return authDao.addAuth(newAuth);
    }

    @Override
    public String login(String identify, String credential) {
        UserAuths user=authDao.findAuthByuCredentialandIdendify(identify,credential);
        String token=jwtUtil.generateToken(user.getUId(),user.getRole());
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        String token=oldToken.replace("Bearer","");
        return jwtUtil.refreshToken(token);
    }
}
