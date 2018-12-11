package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.dto.AdvertiserAndPublicityDto;
import com.htcs.kehaoduo.bs.dto.PublicityDto;
import com.htcs.kehaoduo.bs.po.TPublicity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 推广信息mapper
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
@Repository
public interface TPublicityMapper {

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
     * @param record TPublicity实体
     * @return 是否新增成功
     */
    int insertSelective(TPublicity record);

    /**
     * 根据id查询信息
     *
     * @param id 主键id
     * @return TPublicity实体
     */
    TPublicity selectByPrimaryKey(Integer id);

    /**
     * 根据id修改信息
     *
     * @param record TPublicity实体
     * @return 是否修改成功
     */
    int updateByPrimaryKeySelective(TPublicity record);

    /**
     * 根据当前用户id获取广告信息
     *
     * @param webUserId     当前用户id
     * @param checkStatus   审核状态
     * @param publicityType 推广类型
     * @param contentType   正文类型
     * @return TPublicity集合
     */
    List<TPublicity> selectByWebUserId(@Param("webUserId") Integer webUserId,
                                       @Param("checkStatus") Byte checkStatus,
                                       @Param("publicityType") Byte publicityType,
                                       @Param("contentType") Byte contentType);

    /**
     * 逻辑删除广告信息
     *
     * @param webUserId 当前用户id
     * @param id        主键id
     * @return 是否修改成功
     */
    int logicalDelById(@Param("webUserId") Integer webUserId,
                       @Param("id") Integer id);

    /**
     * 查看全文
     *
     * @param id 广告id
     * @return 所获取的广告信息
     */
    Map<String, Object> queryContent(Integer id);

    /**
     * 查询用户是否读过该广告
     *
     * @param publicityId
     * @param readerOpenid
     * @return
     */
    Integer queryUserHasRead(@Param("publicityId") Integer publicityId, @Param("readerOpenid") String readerOpenid);

    /**
     * 查询详情，并查询阅读量
     *
     * @param id
     * @return
     */
    PublicityDto queryContentWithReadCount(Integer id);

    /**
     * 查看广告的资料 审核
     *
     * @param advertiserAndPublicityDto
     * @return
     */
	List<Map<String,Object>> listPublicity4Check(AdvertiserAndPublicityDto advertiserAndPublicityDto);
}