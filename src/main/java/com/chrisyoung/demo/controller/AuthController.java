package com.chrisyoung.demo.controller;

import com.chrisyoung.demo.domain.UserAuths;
import com.chrisyoung.demo.service.AuthService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @program: tokendemo
 * @author: Chris Young
 * @create: 2018-11-29 10:13
 * @description:
 **/

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/auth/login",method = RequestMethod.GET)
    public ResponseEntity<?> createAuthenticationToken(@Param("dentify") String identify,@Param("credential") String credential ){
        String token=authService.login(identify,credential);
        return ResponseEntity.ok(token);
    }

    @RequestMapping(value = "/auth/register",method = RequestMethod.POST)
    public int register(@RequestBody UserAuths auths){
        return authService.register(auths);
    }

    @RequestMapping(value = "/api",method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public @ResponseBody Object show(){
        return "hello";
    }
}


