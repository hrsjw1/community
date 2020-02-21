package com.example.mapper;

import com.example.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionMapper {

    @Select("select * from question")
    Question questionFind();
}
