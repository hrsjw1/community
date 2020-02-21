package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where id = #{id}")
    User getUserById(int id );

    @Insert("insert into user (userName,passWord,realName) values (#{userName},#{passWord},#{realName})")
    void addUser(User user);
}
