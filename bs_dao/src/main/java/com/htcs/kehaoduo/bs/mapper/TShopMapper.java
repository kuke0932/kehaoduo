package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.po.TShop;
import org.springframework.stereotype.Repository;

/**
 * 店铺Mapper
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
@Repository
public interface TShopMapper {

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
     * @param record TShop实体
     * @return 是否新增成功
     */
    int insertSelective(TShop record);

    /**
     * 根据id查询信息
     *
     * @param id 主键id
     * @return TShop实体
     */
    TShop selectByPrimaryKey(Integer id);

    /**
     * 根据id修改信息
     *
     * @param record TShop实体
     * @return 是否修改成功
     */
    int updateByPrimaryKeySelective(TShop record);

    /**
     * 根据webUserId查询信息
     *
     * @param webUserId 当前登陆用户id
     * @return TShop实体
     */
    TShop selectByWebUserId(Integer webUserId);
}