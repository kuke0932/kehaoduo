package com.htcs.kehaoduo.bs.mapper;

import com.htcs.kehaoduo.bs.po.TUserExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 用户扩展Mapper
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
@Repository
public interface TUserExtMapper {

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
     * @param record TUserExt实体
     * @return 是否新增成功
     */
    int insertSelective(TUserExt record);

    /**
     * 根据id查询信息
     *
     * @param id 主键id
     * @return TUserExt实体
     */
    TUserExt selectByPrimaryKey(Integer id);

    /**
     * 根据id修改信息
     *
     * @param record TUserExt实体
     * @return 是否修改成功
     */
    int updateByPrimaryKeySelective(TUserExt record);

    /**
     * 根据webUserId查询信息
     *
     * @param webUserId 当前用户id
     * @return TUserExt实体
     */
    TUserExt selectByWebUserId(Integer webUserId);

    /**
     * 根据webUserId修改余额
     *
     * @param webUserId  当前用户id
     * @param newBalance 余额
     * @return 是否修改成功
     */
    int updateBalanceByWebUserId(@Param("webUserId") Integer webUserId, @Param("newBalance") BigDecimal newBalance);

    /**
     * 根据openidB查询用户
     *
     * @param openidB
     * @return
     */
    TUserExt selectByOpenidB(String openidB);

    /**
     * 根据openidA查询用户
     *
     * @param openidA
     * @return
     */
    TUserExt selectByOpenidA(String openidA);
}