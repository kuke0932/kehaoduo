package com.htcs.kehaoduo.system.service;

import com.htcs.kehaoduo.system.bean.SysAgent;
import com.htcs.kehaoduo.system.bean.SysAgentAppid;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhaokaiyuan
 * @create 2017-12-07 11:30
 **/
public interface SysAgentService {

    /**
     * 新增代理商
     *
     * @param sysAgent
     * @param sysAgentAppid
     * @return
     */
    boolean addAgent(SysAgent sysAgent, SysAgentAppid sysAgentAppid);

    boolean addAgent(String username, String password, String area);

    boolean updateAgent(SysAgent sysAgent);

    boolean updateAgentWithoutLogged(SysAgent sysAgent);

	/**
	 * 代理商信息列表
	 *
	 * @return
	 */
	List<Map<String,Object>> listAgent(String nameOrTel, Date beginTime, Date endTime);

	void updateAgentAppid(SysAgentAppid sysAgentAppid, String mobile, String telephone);

	void logicalDelAgent(Integer sysUserId);

	void batchRemoveAgent(String ids);

	/**
	 * 根据userId获取代理商信息
	 *
	 * @param id userId
	 * @return
	 */
	Map<String,Object> queryAgentByUserId(Integer id);
}
