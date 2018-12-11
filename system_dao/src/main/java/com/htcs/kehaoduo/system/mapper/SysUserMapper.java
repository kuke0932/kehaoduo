package com.htcs.kehaoduo.system.mapper;

import com.htcs.kehaoduo.system.bean.SysUser;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public interface SysUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByLoginName(String loginName);

    int countByLoginName(String loginName);

	void logicalDelAgent(Integer sysUserId);
}