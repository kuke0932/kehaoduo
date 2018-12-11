package com.htcs.kehaoduo.system.mapper;

import com.htcs.kehaoduo.system.bean.SysAgent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SysAgentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysAgent record);

    int insertSelective(SysAgent record);

    SysAgent selectByPrimaryKey(Integer id);

    SysAgent selectBySysUserId(Integer id);

    int updateByPrimaryKeySelective(SysAgent record);

    int updateBySysUserId(SysAgent record);

    int updateByPrimaryKey(SysAgent record);

	List<Map<String,Object>> listAgent(@Param("nameOrTel") String nameOrTel,
									   @Param("beginTime") Date beginTime,
									   @Param("endTime") Date endTime);

	Map<String,Object> queryAgentByUserId(Integer id);
}