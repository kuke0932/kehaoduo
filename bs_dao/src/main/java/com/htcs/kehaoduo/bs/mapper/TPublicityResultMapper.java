package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.dto.AdvertiserAndPublicityDto;
import com.htcs.kehaoduo.bs.po.TPublicityResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 推广效果Mapper
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
@Repository
public interface TPublicityResultMapper {

    /**
     * 根据id删除信息
     *
     * @param id 主键id
     * @return 是否删除成功
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增信息
     *
     * @param record TPublicityResult实体
     * @return 是否新增成功
     */
    int insertSelective(TPublicityResult record);

    /**
     * 根据id查询信息
     *
     * @param id 主键id
     * @return TPublicityResult实体
     */
    TPublicityResult selectByPrimaryKey(Integer id);

    /**
     * 根据id修改信息
     *
     * @param record TPublicityResult实体
     * @return 是否修改成功
     */
    int updateByPrimaryKeySelective(TPublicityResult record);

    /**
     * 根据publicityId查询扫码次数
     *
     * @param publicityId 广告id
     * @return 扫码次数
     */
    Integer countByPublicityId(Integer publicityId);

    /**
     * 根据publicityId查询发送人数
     *
     * @param publicityId 广告id
     * @return 发送人数
     */
    Integer sumToUserCountByPublicityId(Integer publicityId);

    /**
     * 根据publicityId查询纯文本信息
     *
     * @param publicityId 广告id
     * @return 纯文本信息集合
     */
    List<TPublicityResult> listTextByPublicityId(Integer publicityId);

    /**
     * 根据publicityId查询图文信息
     *
     * @param publicityId 广告id
     * @return 图文信息集合
     */
    Map<String, Object> listImageTextByPublicityId(Integer publicityId);

    /**
     * 根据广告id和扫码者openId修改阅读次数
     *
     * @param publicityId  广告id
     * @param userWechatId 扫码者openId
     * @return
     */
    int updateReadCountByPublicityIdAndUserWechatId(@Param("publicityId") Integer publicityId,
                                                    @Param("userWechatId") String userWechatId);

    /**
     * 根据publicityId查询总阅读数
     *
     * @param publicityId 广告id
     * @return
     */
    int sumReadCountByPublicityId(Integer publicityId);

    /**
     * 获取时间段图文每天点击次数列表
     *
     * @param advertiser 获取广告主消费明细所需参数实体
     * @return
     */
    List<Map<String, Object>> getImgReadCount(AdvertiserAndPublicityDto advertiser);

    /**
     * 获取时间段朋友圈每天点击次数列表
     *
     * @param advertiser 获取广告主消费明细所需参数实体
     * @return
     */
    List<Map<String, Object>> getFriendsReadCount(AdvertiserAndPublicityDto advertiser);
}