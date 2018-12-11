package com.htcs.kehaoduo.bs.service.impl;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.bs.dto.PublicityDto;
import com.htcs.kehaoduo.bs.mapper.TPublicityMapper;
import com.htcs.kehaoduo.bs.mapper.TPublicityReadLogMapper;
import com.htcs.kehaoduo.bs.mapper.TPublicityResultMapper;
import com.htcs.kehaoduo.bs.po.TPublicityReadLog;
import com.htcs.kehaoduo.bs.po.TPublicityResult;
import com.htcs.kehaoduo.bs.service.TPublicityReadLogService;
import com.htcs.kehaoduo.common.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 点击日志业务层实现
 *
 * @author Dragon
 * @create 2017-11-01 10:25
 **/
@Service
public class TPublicityReadLogServiceImpl implements TPublicityReadLogService {

    @Value("${publicity.forward.fee}")
    private String fee;
    @Value("${publicity.forward.user}")
    private String user;
    @Value("${publicity.forward.platform}")
    private String platform;


    @Autowired
    private TPublicityReadLogMapper tPublicityReadLogMapper;

    @Autowired
    private TPublicityResultMapper tPublicityResultMapper;

    @Autowired
    private TPublicityMapper tPublicityMapper;

    private Object lock = new Object();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertPublicityReadLog(TPublicityReadLog tPublicityReadLog) {
        Integer publicityId = tPublicityReadLog.getPublicityId();
        String readerOpenId = tPublicityReadLog.getReaderOpenId();
        String fromOpenId = tPublicityReadLog.getFromOpenId();
        Object o = CacheUtil.getInstance(CacheUtil.CacheType.REDIS).get(readerOpenId);
        Preconditions.checkNotNull(o, "请输入正确的数据!");
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).remove(readerOpenId);
        synchronized (lock) {
            PublicityDto publicityDto = tPublicityMapper.queryContentWithReadCount(publicityId);
            boolean hasEnded = publicityDto.getIsEnded() == 1 || publicityDto.getLogicalDel() == 1;
            System.out.println(hasEnded);
            if (!hasEnded) {
                int count = tPublicityReadLogMapper.countPublicityId(publicityId, readerOpenId);
                if (count < 1) {
                    tPublicityReadLog.setIsValid((byte) 1);
                    int i = 0;
                    String userWechatId = null;
                    if (publicityDto.getPublicityType() == 1) {
                        i = tPublicityResultMapper.updateReadCountByPublicityIdAndUserWechatId(publicityId, fromOpenId);
                        userWechatId = fromOpenId;
                    }
                    if (i == 0) {
                        TPublicityResult tPublicityResult = new TPublicityResult();

                        tPublicityResult.setPublicityId(publicityId);
                        tPublicityResult.setUserWechatId(userWechatId);
                        tPublicityResult.setOperateTime(new Date());
                        tPublicityResult.setReadCount(1);
                        tPublicityResult.setPublicityType(publicityDto.getPublicityType());
                        tPublicityResult.setCreateTime(new Date());
                        tPublicityResultMapper.insertSelective(tPublicityResult);
                    }
                }
                tPublicityReadLog.setReadTime(new Date());
                tPublicityReadLogMapper.insertSelective(tPublicityReadLog);
            }
        }
        return true;
    }


}
