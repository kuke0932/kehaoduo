package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.po.TPublicityPhone;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 客户电话
 *
 * @author shenyahui
 * @create 2017-11-06 13:57
 **/
@Repository
public interface TPublicityPhoneMapper {
    /**
     * 添加客户电话
     *
     * @param tPublicityPhone
     * @return
     */
    int insertPhone(TPublicityPhone tPublicityPhone);

    /**
     * 查询客戶电话、区域、时间
     *
     * @param publicityId
     */
    List<Map<String, Object>> listPhone(Integer publicityId);

}
