package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺实体
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
public class TShop implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 店铺名字
     */
    private String shopName;

    /**
     * 店铺简介
     */
    private String shopDesc;

    /**
     * 地址，多个code以逗号分割
     */
    private String addressCodes;

    /**
     * 经营种类, 多个code以逗号分割
     */
    private String businessLineCodes;

    /**
     * 用户id
     */
    private Integer webUserId;

    private static final long serialVersionUID = 1L;
    /**
     * 身份证正面
     */
    private String cardFront;
    /**
     * 身份证反面
     */
    private String cardBack;
    /**
     * 手持身份证
     */
    private String cardHandheld;
    /**
     * 营业执照
     */
    private String businessLicence;

    /**
     * 逻辑删除标志   1 删除 0 未删除
     */
    private Byte logicalDel;

    /**
     * 创建者ID
     */
    private Integer createBy;

    /**
     * 创建者名字
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者ID
     */
    private Integer updateBy;

    /**
     * 更新者名字
     */
    private String updateName;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 其他证明资料
     */
    private String otherLicence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getAddressCodes() {
        return addressCodes;
    }

    public void setAddressCodes(String addressCodes) {
        this.addressCodes = addressCodes;
    }

    public String getBusinessLineCodes() {
        return businessLineCodes;
    }

    public void setBusinessLineCodes(String businessLineCodes) {
        this.businessLineCodes = businessLineCodes;
    }

    public Integer getWebUserId() {
        return webUserId;
    }

    public void setWebUserId(Integer webUserId) {
        this.webUserId = webUserId;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public String getCardHandheld() {
        return cardHandheld;
    }

    public void setCardHandheld(String cardHandheld) {
        this.cardHandheld = cardHandheld;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getOtherLicence() {
        return otherLicence;
    }

    public void setOtherLicence(String otherLicence) {
        this.otherLicence = otherLicence;
    }

    public Byte getLogicalDel() {
        return logicalDel;
    }

    public void setLogicalDel(Byte logicalDel) {
        this.logicalDel = logicalDel;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TShop other = (TShop) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getShopName() == null ? other.getShopName() == null : this.getShopName().equals(other.getShopName()))
            && (this.getShopDesc() == null ? other.getShopDesc() == null : this.getShopDesc().equals(other.getShopDesc()))
            && (this.getAddressCodes() == null ? other.getAddressCodes() == null : this.getAddressCodes().equals(other.getAddressCodes()))
            && (this.getBusinessLineCodes() == null ? other.getBusinessLineCodes() == null : this.getBusinessLineCodes().equals(other.getBusinessLineCodes()))
            && (this.getWebUserId() == null ? other.getWebUserId() == null : this.getWebUserId().equals(other.getWebUserId()))
            && (this.getCardFront() == null ? other.getCardFront() == null : this.getCardFront().equals(other.getCardFront()))
            && (this.getCardBack() == null ? other.getCardBack() == null : this.getCardBack().equals(other.getCardBack()))
            && (this.getCardHandheld() == null ? other.getCardHandheld() == null : this.getCardHandheld().equals(other.getCardHandheld()))
            && (this.getBusinessLicence() == null ? other.getBusinessLicence() == null : this.getBusinessLicence().equals(other.getBusinessLicence()))
            && (this.getOtherLicence() == null ? other.getOtherLicence() == null : this.getOtherLicence().equals(other.getOtherLicence()))
            && (this.getLogicalDel() == null ? other.getLogicalDel() == null : this.getLogicalDel().equals(other.getLogicalDel()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateName() == null ? other.getUpdateName() == null : this.getUpdateName().equals(other.getUpdateName()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getShopName() == null) ? 0 : getShopName().hashCode());
        result = prime * result + ((getShopDesc() == null) ? 0 : getShopDesc().hashCode());
        result = prime * result + ((getAddressCodes() == null) ? 0 : getAddressCodes().hashCode());
        result = prime * result + ((getBusinessLineCodes() == null) ? 0 : getBusinessLineCodes().hashCode());
        result = prime * result + ((getWebUserId() == null) ? 0 : getWebUserId().hashCode());
        result = prime * result + ((getCardFront() == null) ? 0 : getCardFront().hashCode());
        result = prime * result + ((getCardBack() == null) ? 0 : getCardBack().hashCode());
        result = prime * result + ((getCardHandheld() == null) ? 0 : getCardHandheld().hashCode());
        result = prime * result + ((getBusinessLicence() == null) ? 0 : getBusinessLicence().hashCode());
        result = prime * result + ((getOtherLicence() == null) ? 0 : getOtherLicence().hashCode());
        result = prime * result + ((getLogicalDel() == null) ? 0 : getLogicalDel().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateName() == null) ? 0 : getCreateName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateName() == null) ? 0 : getUpdateName().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", shopName=").append(shopName);
        sb.append(", shopDesc=").append(shopDesc);
        sb.append(", addressCodes=").append(addressCodes);
        sb.append(", businessLineCodes=").append(businessLineCodes);
        sb.append(", webUserId=").append(webUserId);
        sb.append(", cardFront=").append(cardFront);
        sb.append(", cardBack=").append(cardBack);
        sb.append(", cardHandheld=").append(cardHandheld);
        sb.append(", businessLicence=").append(businessLicence);
        sb.append(", otherLicence=").append(otherLicence);
        sb.append(", logicalDel=").append(logicalDel);
        sb.append(", createBy=").append(createBy);
        sb.append(", createName=").append(createName);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateName=").append(updateName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}