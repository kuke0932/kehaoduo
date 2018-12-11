package com.htcs.kehaoduo.system.mapper;

import com.htcs.kehaoduo.system.bean.SysAgentAppid;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAgentAppidMapper {

    SysAgentAppid selectByAppId(String appId);

    SysAgentAppid selectBySysUserId(Integer sysUserId);

    SysAgentAppid selectByAreaCode(String  areaCode);

    int deleteByPrimaryKey(Integer id);

    int insert(SysAgentAppid record);

    int insertSelective(SysAgentAppid record);

    SysAgentAppid selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAgentAppid record);

	void updateBySysUserId(SysAgentAppid sysAgentAppid);
}
