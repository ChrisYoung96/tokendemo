package com.chrisyoung.demo.utils;

import com.chrisyoung.demo.security.CustomHttpServletRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: tokendemo
 * @author: Chris Young
 * @create: 2018-11-28 08:24
 * @description:
 **/

@Component
public class JWTUtil {
    //private static final String SECRET=UUID.randomUUID().toString().replace("-","a")+UUID.randomUUID().toString().replace("-","a")+UUID.randomUUID().toString().replace("-","a");
    private static final String SECRET="d2f0f06fa5dd6a479faa3aaa6034f7dc507953ec30f3a6292a48f4a8341abacca717302e1b8f16f8aa608a4d0caa049afca28dc7a081";
    private static final long EXPIRATION_TIME=604_800_000L; //有效期一周
    private static final String TOKEN_PREFIX="Bearer"; //token头部
    private static final String HEADER_STRING="Authorization"; //将token以键值对形式放入请求的头部，key的值为"Authroization"

    /**
     * 生成token
     * @param uId 用户id
     * @param role 用户权限
     * @return token
     */
    public  String generateToken(String uId,String role){
        HashMap<String,Object> map=new HashMap<>(); //用于存储放入token中的内容
        map.put("UserId",uId); //将用户ID放入token
        map.put("Role",role);  //将用户权限放入token
        map.put("CreatedDate",new Date());  //将token创建日期放入token
        return generateToken(map);  //生成token
    }

    /**
     * 使用JWT生成token
     * @param claims 放入token中的内容
     * @return token
     */
    private String generateToken(Map<String,Object> claims){
        String jwt=Jwts.builder()
                .setClaims(claims)  //设置内容
                .setExpiration(new Date(System.currentTimeMillis()
                        +EXPIRATION_TIME))  //设置token有效期
                .signWith(SignatureAlgorithm.HS512,SECRET)  //加密
                .compact();
        return TOKEN_PREFIX+jwt;
    }

    public  HttpServletRequest validateTokenAndAddUserIdToHeader(HttpServletRequest request){
        String token=request.getHeader(HEADER_STRING);
        if(token!=null){
            Map<String,Object> body=getClaimsFromToken(token);
            return new CustomHttpServletRequest(request,body);
        }
        return null;
    }

    /**
     * 解密token获取token中的内容
     * @param token 用户的token
     * @return token中的内容
     */
    private Claims getClaimsFromToken(String token){
        Claims claims;
        try{
            claims= Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                    .getBody();
        }catch (Exception e){
            claims=null;
        }
        return claims;
    }

    /**
     * 从token中获取用户权限
     * @param token 用户token
     * @return 权限
     */
    public String getRoleFromToken(String token){
        String role;
        try {
            final Claims claims=getClaimsFromToken(token);
            role= (String) claims.get("Role");
        }catch (Exception e){
            role="";
        }
        return role;
    }

    /**
     * 从token中获取用户ID
     * @param token
     * @return
     */
    public String getUserIdFromToken(String token){
        String uId;
        try{
            final Claims claims=getClaimsFromToken(token);
            uId= (String) claims.get("UserId");
        }catch (Exception e){
            uId="";
        }
        return uId;
    }

    /**
     * 从token中获取token创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token){
        Date createDate;
        try{
            final Claims claims=getClaimsFromToken(token);
            createDate=new Date((Long)claims.get("CreatedDate"));
        }catch (Exception  e){
            createDate=null;
        }
        return createDate;
    }

    /**
     * 获取token的过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token){
        Date expiration;
        try {
            final Claims claims=getClaimsFromToken(token);
            expiration=claims.getExpiration();
        }catch (Exception e){
            expiration=null;
        }
        return expiration;
    }


    /**
     * 检查token是否过期
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token){
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }



    /**
     * 如果token过期，刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token){
        String refreshToken;
        try {
            final Claims claims=getClaimsFromToken(token);
            claims.put("CreatedDate",new Date());
            refreshToken=generateToken(claims);
        }catch (Exception e){
            refreshToken="";
        }
        return refreshToken;
    }


    /**
     * 验证token
     * @param token
     * @return
     */
    public Boolean isValidatedToken(String token){
        Boolean result1=false;
        Boolean result2=false;
        Boolean result3=false;

        if(token.startsWith(TOKEN_PREFIX))
            result1=true;
        if(getClaimsFromToken(token)!=null)
            result2=true;
        if(!isTokenExpired(token))
            result3=true;
        return(result1&&result2&&result3);
    }
}
