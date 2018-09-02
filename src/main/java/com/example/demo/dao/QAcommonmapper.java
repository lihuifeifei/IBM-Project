package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.po.QAcommonEntity;

@Mapper
public interface QAcommonmapper {
	@Select("SELECT * FROM qacommon")
	List<QAcommonEntity> selectall();
}
