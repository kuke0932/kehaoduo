package com.htcs.kehaoduo.bs.service;


import com.htcs.kehaoduo.bs.dto.AdvertiserAndPublicityDto;
import com.htcs.kehaoduo.bs.dto.PublicityDto;
import com.htcs.kehaoduo.bs.po.TPublicity;
import com.htcs.kehaoduo.bs.po.TPublicityCheckLog;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 广告业务层接口
 *
 * @author Dragon
 * @create 2017-10-20 15:07
 **/
public interface TPublicityService {

    /**
     * 新增广告信息
     *
     * @param publicity TPublicity实体
     */
    void addPublicity(TPublicity publicity);

    /**
     * 添加广告信息，设置statisUrl
     * @param publicity
     * @param appId
     * @throws UnsupportedEncodingException
     * @throws WxErrorException
     */
    @Transactional(rollbackFor = Exception.class)
    void addPublicityAndSetStaticUrl(TPublicity publicity, String appId) throws UnsupportedEncodingException, WxErrorException;

    /**
     * 根据当前用户id获取广告信息
     *
     * @param checkStatus   审核状态0 未填写 1 已保存2 未审核 3 审核通过 4 审核不通过
     * @param publicityType 推广类型(1扫码，2转发)
     * @param contentType   正文类型(1文本2图文)
     * @return TPublicity集合
     */
    List<TPublicity> listPublicityByUserId(Byte checkStatus, Byte publicityType, Byte contentType);

    /**
     * 通过id逻辑删除广告信息
     *
     * @param id 主键id
     * @return 是否删除成功
     */
    int logicalDelById(Integer id);

    /**
     * 通过id删除广告信息
     *
     * @param id 主键id
     * @return 是否删除成功
     */
    int deletePublicityById(Integer id);

    /**
     * 修改广告信息
     *
     * @param publicity TPublicity实体
     * @return 是否修改成功
     */
    void updatePublicity(TPublicity publicity);

    /**
     * 修改广告审核信息
     *
     * @param publicity TPublicity实体
     * @return 是否修改成功
     */
    void updatePublicityCheckStatus(TPublicity publicity);


    /**
     * 根据id获取广告信息
     *
     * @param id 主键id
     * @return 所获取的广告信息
     */
    TPublicity queryPublicity(Integer id);

    /**
     * 根据id获取广告信息和扩展信息
     *
     * @param id 广告id
     * @return 所获取的广告信息
     */
    TPublicity queryPublicityAndExt(Integer id);

    /**
     * 开始推广
     *
     * @param id 广告id
     * @return
     */
    Map<String, Object> beginExtend(Integer id);

    /**
     * 查看全文
     *
     * @param id 广告id
     * @return 所获取的广告信息
     */
    Map<String, Object> queryContent(Integer id);

    /**
     * 查询详情，并查询阅读量
     *
     * @param id
     * @return
     */
    PublicityDto queryContent4Click(Integer id, String fromOpenId, String address);

    /**
     * 查看广告的资料 审核
     *
     * @param advertiserAndPublicityDto
     * @return
     */
	List<Map<String,Object>> listPublicity4Check(AdvertiserAndPublicityDto advertiserAndPublicityDto);

    /**
     * 审核广告
     *
     * @param tPublicityCheckLog
     */
    void checkPublicity(TPublicityCheckLog tPublicityCheckLog);
}
