package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.po.TPublicityReadLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 点击日志Mapper
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
@Repository
public interface TPublicityReadLogMapper {

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
     * @param record TPublicityReadLog实体
     * @return 是否新增成功
     */
    int insertSelective(TPublicityReadLog record);

    /**
     * 根据id查询信息
     *
     * @param id 主键id
     * @return TPublicityReadLog实体
     */
    TPublicityReadLog selectByPrimaryKey(Integer id);

    /**
     * 根据id修改信息
     *
     * @param record TPublicityReadLog实体
     * @return 是否修改成功
     */
    int updateByPrimaryKeySelective(TPublicityReadLog record);

    /**
     * 检查是否已经阅读过
     *
     * @param publicityId  广告id
     * @param readerOpenId 读者openId
     * @return
     */
    int countPublicityId(@Param("publicityId") Integer publicityId,
                         @Param("readerOpenId") String readerOpenId);
}