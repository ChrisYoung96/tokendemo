package com.chrisyoung.demo.dao;

import com.chrisyoung.demo.domain.UserAuths;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @program: tokendemo
 * @author: Chris Young
 * @create: 2018-11-28 16:05
 * @description:
 **/
@Mapper
@Repository
public interface IAuthDao {
    //显示某用户的所有验证条目
    @Select("select * from user_auths where identify=#{identify} and credential=#{credential}")
    @Results({
            @Result(property = "uaId",column = "ua_id"),
            @Result(property = "uId",column = "u_id"),
            @Result(property = "identify",column = "identify"),
            @Result(property = "credential",column = "credential"),
            @Result(property = "role",column = "role")
    })
    UserAuths findAuthByuCredentialandIdendify(@Param("identify") String identify,@Param("credential") String credential);

    @Select("select * from user_auths where u_id=#{uId}")
    @Results({
            @Result(property = "uaId",column = "ua_id"),
            @Result(property = "uId",column = "u_id"),
            @Result(property = "identify",column = "identify"),
            @Result(property = "credential",column = "credential"),
            @Result(property = "role",column = "role")
    })
    UserAuths findAuthbyuId(@Param("uId") String uId);

    @Insert("insert into user_auths(u_id,identify,credential,role) values(#{uId},#{identify},#{credential},#{role})")
    int addAuth(UserAuths auth);
}
