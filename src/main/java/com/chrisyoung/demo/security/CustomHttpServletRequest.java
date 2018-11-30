package com.chrisyoung.demo.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @program: tokendemo
 * @author: Chris Young
 * @create: 2018-11-28 11:13
 * @description:
 **/


public class CustomHttpServletRequest extends HttpServletRequestWrapper {


    private Map<String,String> claims;
    public CustomHttpServletRequest(HttpServletRequest request,Map<String,?>claims) {
        super(request);
        this.claims=new HashMap<>();
        claims.forEach((k,v)->this.claims.put(k,String.valueOf(v)));
    }

    @Override
    public Enumeration<String> getHeaders(String name){
        if(claims !=null && claims.containsKey(name)){
            return Collections.enumeration(Arrays.asList(claims.get(name)));
        }
        return super.getHeaders(name);
    }

    public Map<String, String> getClaims() {
        return claims;
    }



}

