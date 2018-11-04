package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QAlogmapper {
	@Insert("INSERT INTO qalog VALUES(null,#{q},#{a},NULL)")
	int insertItem(@Param("q")String q,@Param("a")String a);

}
