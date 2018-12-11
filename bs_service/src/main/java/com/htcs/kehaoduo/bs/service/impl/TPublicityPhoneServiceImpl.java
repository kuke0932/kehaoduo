package com.htcs.kehaoduo.bs.service.impl;

import com.htcs.kehaoduo.bs.mapper.TPublicityPhoneMapper;
import com.htcs.kehaoduo.bs.po.TPublicityPhone;
import com.htcs.kehaoduo.bs.service.TPublicityPhoneSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 客户电话
 *
 * @author shenyahui
 * @create 2017-11-06 14:13
 **/
@Service
public class TPublicityPhoneServiceImpl implements TPublicityPhoneSevice {

    @Autowired
    private TPublicityPhoneMapper tPublicityPhoneMapper;

    @Override
    public int insertPhone(TPublicityPhone tPublicityPhone) {
        tPublicityPhone.setCreateTime(new Date());
        return tPublicityPhoneMapper.insertPhone(tPublicityPhone);
    }

}
