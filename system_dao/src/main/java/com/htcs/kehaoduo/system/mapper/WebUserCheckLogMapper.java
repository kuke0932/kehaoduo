package com.htcs.kehaoduo.system.mapper;

import com.htcs.kehaoduo.system.bean.WebUserCheckLog;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserCheckLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WebUserCheckLog record);

    int insertSelective(WebUserCheckLog record);

    WebUserCheckLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebUserCheckLog record);

    int updateByPrimaryKey(WebUserCheckLog record);
}