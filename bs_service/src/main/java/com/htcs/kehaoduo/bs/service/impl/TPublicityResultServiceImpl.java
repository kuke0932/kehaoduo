package com.htcs.kehaoduo.bs.service.impl;

import com.htcs.kehaoduo.bs.mapper.TPublicityPhoneMapper;
import com.htcs.kehaoduo.bs.mapper.TPublicityResultMapper;
import com.htcs.kehaoduo.bs.po.TPublicityResult;
import com.htcs.kehaoduo.bs.service.TPublicityResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推广效果业务层实现
 *
 * @author Dragon
 * @create 2017-10-23 16:42
 **/
@Service
public class TPublicityResultServiceImpl implements TPublicityResultService {

    @Autowired
    private TPublicityResultMapper tPublicityResultMapper;

    @Autowired
    private TPublicityPhoneMapper tPublicityPhoneMapper;

    @Override
    public Map<String, Object> listByPublicityId(Integer publicityId, Byte contentType) {
        Map<String, Object> map = new HashMap<>();
        if (contentType == 1) {
            // 文字
            Integer scanCount = tPublicityResultMapper.countByPublicityId(publicityId);
            Integer sendCount = tPublicityResultMapper.sumToUserCountByPublicityId(publicityId);
            List<TPublicityResult> publicityResult = tPublicityResultMapper.listTextByPublicityId(publicityId);
            map.put("scanCount", scanCount);
            map.put("sendCount", sendCount);
            map.put("publicityResult", publicityResult);
            return map;
        }
        if (contentType == 2) {
            // 图文
            List<Map<String, Object>> phoneList = tPublicityPhoneMapper.listPhone(publicityId);
            Map<String, Object> publicityResult = tPublicityResultMapper.listImageTextByPublicityId(publicityId);
            map.put("phoneList", phoneList);
            map.put("publicityResult", publicityResult);
            return map;
        }
        return null;
    }

    @Override
    public int addPublicityResult(TPublicityResult tPublicityResult) {
        return tPublicityResultMapper.insertSelective(tPublicityResult);
    }
}
