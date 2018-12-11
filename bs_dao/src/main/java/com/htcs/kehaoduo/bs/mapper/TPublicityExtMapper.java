package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.po.TPublicity;
import com.htcs.kehaoduo.bs.po.TPublicityExt;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 推广信息扩展mapper
 *
 * @author Dragon
 * @create 2017-11-22 9:09
 **/
@Repository
public interface TPublicityExtMapper {

    /**
     * 新增信息
     *
     * @param tPublicityExt TPublicityExt实体
     */
    void insertSelective(TPublicityExt tPublicityExt);

    /**
     * 根据推广id删除信息
     *
     * @param publicityId 推广id
     */
    void deleteByPublicityId(Integer publicityId);

    /**
     * 根据推广id查询正文数据集合
     *
     * @param publicityId 推广id
     * @return 正文数据集合
     */
    List<TPublicity.DraftContent> listByPublicity(Integer publicityId);
}
