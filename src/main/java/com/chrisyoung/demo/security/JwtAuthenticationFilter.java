package com.chrisyoung.demo.security;

import com.chrisyoung.demo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @program: tokendemo
 * @author: Chris Young
 * @create: 2018-11-28 09:22
 * @description:
 **/


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JWTUtil jwtUtil = new JWTUtil();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
                String token = httpServletRequest.getHeader("Authorization");
                if(token!=null){
                    String role=jwtUtil.getRoleFromToken(token);
                    if( SecurityContextHolder.getContext().getAuthentication()==null){
                        if(jwtUtil.isValidatedToken(token)){
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null,null, Arrays.asList(() -> role));
                            //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
                //httpServletRequest = jwtUtil.validateTokenAndAddUserIdToHeader(httpServletRequest);

        } catch (Exception e) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
