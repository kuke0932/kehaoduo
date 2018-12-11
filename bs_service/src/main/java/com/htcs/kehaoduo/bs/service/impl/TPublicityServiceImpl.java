package com.htcs.kehaoduo.bs.service.impl;

import com.google.common.base.Preconditions;
import com.htcs.kehaoduo.bs.dto.AdvertiserAndPublicityDto;
import com.htcs.kehaoduo.bs.dto.PublicityDto;
import com.htcs.kehaoduo.bs.mapper.TPublicityCheckLogMapper;
import com.htcs.kehaoduo.bs.mapper.TPublicityExtMapper;
import com.htcs.kehaoduo.bs.mapper.TPublicityMapper;
import com.htcs.kehaoduo.bs.mapper.TPublicityResultMapper;
import com.htcs.kehaoduo.bs.po.TPublicity;
import com.htcs.kehaoduo.bs.po.TPublicityCheckLog;
import com.htcs.kehaoduo.bs.po.TPublicityExt;
import com.htcs.kehaoduo.bs.service.TPublicityService;
import com.htcs.kehaoduo.bs.wechatapi.WechatApiMap;
import com.htcs.kehaoduo.bs.wechatapi.api.WechatApi;
import com.htcs.kehaoduo.bs.wechatapi.model.Environment;
import com.htcs.kehaoduo.common.conf.RequestUserHolder;
import com.htcs.kehaoduo.common.model.LoginSysUser;
import com.htcs.kehaoduo.common.model.LoginWebUser;
import com.htcs.kehaoduo.common.util.CacheUtil;
import com.htcs.kehaoduo.common.util.UUIDUtil;
import com.htcs.kehaoduo.wechat.conf.WxMpServices;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 广告业务层实现
 *
 * @author Dragon
 * @create 2017-10-20 15:08
 **/
@Service
public class TPublicityServiceImpl implements TPublicityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TPublicityServiceImpl.class);

    @Autowired
    private TPublicityMapper tPublicityMapper;

    @Autowired
    private TPublicityResultMapper tPublicityResultMapper;

    @Autowired
    private TPublicityExtMapper tPublicityExtMapper;

    @Autowired
    private TPublicityCheckLogMapper tPublicityCheckLogMapper;

    @Value("${site}")
    private String site;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPublicity(TPublicity publicity) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        Integer userId = loginWebUser.getId();
        String userName = loginWebUser.getUserName();

        Date now = new Date();
        publicity.setCreateBy(userId);
        publicity.setCreateName(userName);
        publicity.setCreateTime(now);
        publicity.setWebUserId(userId);
        tPublicityMapper.insertSelective(publicity);
        if (publicity.getContentType() == 2) {
            for (TPublicity.DraftContent draftContent : publicity.getContentList()) {
                TPublicityExt tPublicityExt = new TPublicityExt();
                tPublicityExt.setPublicityId(publicity.getId());
                tPublicityExt.setCreateBy(userId);
                tPublicityExt.setCreateName(userName);
                tPublicityExt.setCreateTime(now);
                if (StringUtils.isNotEmpty(draftContent.getContentText().trim())) {
                    tPublicityExt.setContentText(draftContent.getContentText());
                }
                if (StringUtils.isNotEmpty(draftContent.getContentImage().trim())) {
                    tPublicityExt.setContentImage(draftContent.getContentImage());
                }
                tPublicityExtMapper.insertSelective(tPublicityExt);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPublicityAndSetStaticUrl(TPublicity publicity, String appId) throws UnsupportedEncodingException, WxErrorException {
        addPublicity(publicity);
        setStaticUrl(publicity, appId);
        tPublicityMapper.updateByPrimaryKeySelective(publicity);


    }

    private void setStaticUrl(TPublicity publicity, String appId) throws UnsupportedEncodingException, WxErrorException {
        if(appId != null && publicity.getPublicityType() != null && publicity.getPublicityType() ==  1  && publicity.getContentType() != null && publicity.getContentType() == 2) {
            WxMpServices instance = WxMpServices.getInstance(appId);
            WxMpService wxMpService = instance.getWxMpService();
            String oAuthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
            String redirectUrl = "http://" + site + "/web/host-user/img_page/img_show_send.html?id=" + publicity.getId();
            redirectUrl = oAuthUrl.replace("REDIRECTURL", URLEncoder.encode(redirectUrl, "UTF-8"));
            String s = wxMpService.shortUrl(redirectUrl);
            publicity.setStaticUrl(s);
        }
    }

    @Override
    public List<TPublicity> listPublicityByUserId(Byte checkStatus, Byte publicityType, Byte contentType) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        return tPublicityMapper.selectByWebUserId(loginWebUser.getId(), checkStatus, publicityType, contentType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int logicalDelById(Integer id) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        Integer webUserId = loginWebUser.getId();

        TPublicity publicity = tPublicityMapper.selectByPrimaryKey(id);
        // 逻辑删除广告信息
        return tPublicityMapper.logicalDelById(webUserId, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deletePublicityById(Integer id) {
        tPublicityExtMapper.deleteByPublicityId(id);
        return tPublicityMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePublicity(TPublicity publicity) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        Integer userId = loginWebUser.getId();
        String userName = loginWebUser.getUserName();
        Integer publicityId = publicity.getId();

        Date now = new Date();
        publicity.setUpdateBy(userId);
        publicity.setUpdateName(userName);
        publicity.setUpdateTime(now);
        if (publicity.getContentType() == 2) {
            tPublicityExtMapper.deleteByPublicityId(publicityId);
            for (TPublicity.DraftContent draftContent : publicity.getContentList()) {
                TPublicityExt tPublicityExt = new TPublicityExt();
                tPublicityExt.setPublicityId(publicityId);
                tPublicityExt.setCreateBy(userId);
                tPublicityExt.setCreateName(userName);
                tPublicityExt.setCreateTime(now);
                if (StringUtils.isNotEmpty(draftContent.getContentText().trim())) {
                    tPublicityExt.setContentText(draftContent.getContentText());
                }
                if (StringUtils.isNotEmpty(draftContent.getContentImage().trim())) {
                    tPublicityExt.setContentImage(draftContent.getContentImage());
                }
                tPublicityExtMapper.insertSelective(tPublicityExt);
            }
        }
        tPublicityMapper.updateByPrimaryKeySelective(publicity);
    }

    @Override
    public void updatePublicityCheckStatus(TPublicity publicity) {
        LoginWebUser loginWebUser = RequestUserHolder.currentWebUser();
        Preconditions.checkNotNull(loginWebUser, "当前用户未登录或登录超时，请重新登录！");
        Integer userId = loginWebUser.getId();
        String userName = loginWebUser.getUserName();

        Date now = new Date();
        publicity.setUpdateBy(userId);
        publicity.setUpdateName(userName);
        publicity.setUpdateTime(now);
        tPublicityMapper.updateByPrimaryKeySelective(publicity);

    }

    @Override
    public TPublicity queryPublicity(Integer id) {
        return tPublicityMapper.selectByPrimaryKey(id);
    }

    @Override
    public TPublicity queryPublicityAndExt(Integer id) {
        TPublicity publicity = tPublicityMapper.selectByPrimaryKey(id);
        List<TPublicity.DraftContent> contentList = tPublicityExtMapper.listByPublicity(id);
        publicity.setContentList(contentList);
        return publicity;
    }

    @Override
    public Map<String, Object> beginExtend(Integer id) {
        TPublicity publicity = tPublicityMapper.selectByPrimaryKey(id);

        Environment environment = Environment.of("classpath:config.properties");
        WechatApi wechatApi = new WechatApi(environment);

        wechatApi.getUUID();

        // 生成key
        String uuid = UUIDUtil.getUUID();

        // 将WechatApi实例存入redis，key为uuid
        WechatApiMap.put(uuid, wechatApi);

        // 二维码url
        String qrCodeUrl = wechatApi.genqrcodeurl();

        Map<String, Object> map = new HashMap<>();
        map.put("qrCodeUrl", qrCodeUrl);
        map.put("publicity", publicity);
        map.put("uuid", uuid);
        return map;
    }

    @Override
    public Map<String, Object> queryContent(Integer id) {
        return tPublicityMapper.queryContent(id);

    }

    @Override
    public PublicityDto queryContent4Click(Integer id, String readerOpenid, String address) {

        // 将readeropenid加入缓存，在insert read log时取出来，防止用户恶意刷请求
        CacheUtil.getInstance(CacheUtil.CacheType.REDIS).put(readerOpenid, "1", 60, TimeUnit.SECONDS);

        PublicityDto publicity = tPublicityMapper.queryContentWithReadCount(id);
        String validAreaCode = (String) publicity.getValidAreaCode();
        String[] validAreaCodes = validAreaCode.replaceAll(",", "")
                .replaceAll("不限", "").split(";");
        LocalDate l = LocalDate.now();

        boolean inValidAreaCode = false;
        for (String code : validAreaCodes) {

            if (address.contains(code) || code.equals("全国")) {
                inValidAreaCode = true;
                break;
            }
        }
        publicity.setInValidAreaCode(inValidAreaCode);
        boolean userHasRead = false;
        if (inValidAreaCode) {
            Byte logicalDel = publicity.getLogicalDel();
            Byte isEnded = publicity.getIsEnded();
            if (logicalDel == 1 || isEnded == 1) {
                if (StringUtils.isNotEmpty(readerOpenid)) {
                    Integer c = tPublicityMapper.queryUserHasRead(id, readerOpenid);
                    if (c != null && c > 0) {
                        userHasRead = true;
                    }
                }
            }
        }
        publicity.setUserHasRead(userHasRead);
        publicity.setReaderOpenid(readerOpenid);
        return publicity;
    }

    @Override
    public List<Map<String, Object>> listPublicity4Check(AdvertiserAndPublicityDto advertiserAndPublicityDto) {
        return tPublicityMapper.listPublicity4Check(advertiserAndPublicityDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkPublicity(TPublicityCheckLog tPublicityCheckLog) {
        LoginSysUser sysUser = RequestUserHolder.currentSysUser();
        Preconditions.checkNotNull(sysUser, "当前用户未登录或登录超时，请重新登录！！");
        TPublicity tPublicity = tPublicityMapper.selectByPrimaryKey(tPublicityCheckLog.getPublicityId());

        Preconditions.checkArgument(Objects.equals(tPublicityCheckLog.getFromStatus(), tPublicity.getCheckStatus()), "请刷新页面后重新操作");

        TPublicity publicity = new TPublicity();
        publicity.setId(tPublicityCheckLog.getPublicityId());
        publicity.setCheckStatus(tPublicityCheckLog.getToStatus());
        publicity.setCheckResult(tPublicityCheckLog.getCheckResult());
        publicity.setUpdateBy(sysUser.getId());
        publicity.setUpdateName(sysUser.getUserName());
        publicity.setUpdateTime(new Date());
        int i = tPublicityMapper.updateByPrimaryKeySelective(publicity);
        if (i > 0) {
            tPublicityCheckLog.setCreateBy(sysUser.getId());
            tPublicityCheckLog.setCreateName(sysUser.getUserName());
            tPublicityCheckLog.setCreateTime(new Date());
            tPublicityCheckLogMapper.insertSelective(tPublicityCheckLog);
        }
    }
}
