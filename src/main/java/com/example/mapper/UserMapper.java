package com.example.mapper;

import model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,accountId,token,gmtCreate,gmtModified) values ('${name}','${accountId}','${token}',${gmtCreate},${gmtModified})")
    void insert(User user);

    @Select("select * from user limit 1")
    User findByToken();
}
