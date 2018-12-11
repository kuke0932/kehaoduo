package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.po.TPublicityCheckLog;
import org.springframework.stereotype.Repository;

@Repository
public interface TPublicityCheckLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TPublicityCheckLog record);

    int insertSelective(TPublicityCheckLog record);

    TPublicityCheckLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPublicityCheckLog record);

    int updateByPrimaryKey(TPublicityCheckLog record);
}