package com.htcs.kehaoduo.bs.controller;

import com.htcs.kehaoduo.bs.service.TPublicityResultService;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 推广效果控制层
 *
 * @author Dragon
 * @create 2017-10-23 16:30
 **/
@RestController
@RequestMapping("/web/publicityResult")
public class TPublicityResultController {

    @Autowired
    private TPublicityResultService tPublicityResultService;

    /**
     * 查询文本和图文的推广效果，
     * contentType（1：文本，2：图文）
     *
     * @param publicityId 广告id
     * @param contentType 正文类型（1：文本，2：图文）
     * @return JSON数据
     */
    @GetMapping("/selectPublicityResult")
    public ReturnStatus selectPublicityResult(Integer publicityId, Byte contentType) {
        Map<String, Object> map = tPublicityResultService.listByPublicityId(publicityId, contentType);
        return new ReturnStatus(ReturnStatus.StatusCode.OK, "查询成功", map);
    }

}
