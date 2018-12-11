package com.htcs.kehaoduo.bs.service;


import com.htcs.kehaoduo.bs.po.TPublicityReadLog;

/**
 * 点击日志业务层接口
 *
 * @author Dragon
 * @create 2017-11-01 10:23
 **/
public interface TPublicityReadLogService {

    /**
     * 生成点击日志
     *
     * @param tPublicityReadLog
     * @return
     */
    Boolean insertPublicityReadLog(TPublicityReadLog tPublicityReadLog);
}
