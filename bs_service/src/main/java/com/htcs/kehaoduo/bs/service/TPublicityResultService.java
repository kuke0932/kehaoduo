package com.htcs.kehaoduo.bs.service;


import com.htcs.kehaoduo.bs.po.TPublicityResult;

import java.util.Map;

/**
 * 推广效果业务层
 *
 * @author Dragon
 * @create 2017-10-23 16:32
 **/
public interface TPublicityResultService {

    /**
     * 根据publicityId查询推广效果信息
     *
     * @param publicityId 广告id
     * @param contentType 广告类型 1文本，2图文
     * @return 推广效果集合
     */
    Map<String, Object> listByPublicityId(Integer publicityId, Byte contentType);

    /**
     * 新增信息
     *
     * @param tPublicityResult TPublicityResult实体
     * @return 是否添加成功
     */
    int addPublicityResult(TPublicityResult tPublicityResult);
}
