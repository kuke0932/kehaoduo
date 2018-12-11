package com.htcs.kehaoduo.bs.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 推广信息实体
 *
 * @author Dragon
 * @create 2017-10-20 9:47
 **/
public class TPublicity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 图片地址
     */
    private String firstImageUrl;
    /**
     * 标题
     */
    private String publicityTitle;
    /**
     * 简介
     */
    private String publicityIntro;
    /**
     * 推广类型(1扫码，2转发)
     */
    private Byte publicityType;
    /**
     * 审核状态0 未提交审核 1 已保存2 待审核 3 审核通过 4 审核不通过 5 取消审核
     */
    private Byte checkStatus;
    /**
     * 最近一次审核结果
     */
    private String checkResult;

    /**
     * 正文类型(1文本2图文)
     */
    private Byte contentType;
    /**
     * 推广二维码地址
     */
    private String qrcodeUrl;
    /**
     * 推广费用
     */
    private String fee;
    /**
     * 全部推广次数
     */
    private Integer totalReadCount;
    /**
     * 是否结束0 未结束 1已结束
     */
    private Byte isEnded;
    /**
     * 有效区域，(省市县三级，以逗号分隔)
     */
    private String validAreaCode;
    /**
     * 是否启用客户电话功能 0 不启用1 启用
     */
    private Byte isContainPhone;
    /**
     * 所属于用户ID
     */
    private Integer webUserId;
    /**
     * 发送给用户类型1全部用户2男性朋友3女性朋友4群，多选中间以","隔开
     */
    private String toUserType;
    /**
     * 生成的静态地址
     */
    private String staticUrl;
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
     * 正文
     */
    private String content;
    /**
     * 正文数据集合
     */
    private List<DraftContent> contentList;

    public List<DraftContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<DraftContent> contentList) {
        this.contentList = contentList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }

    public String getPublicityTitle() {
        return publicityTitle;
    }

    public void setPublicityTitle(String publicityTitle) {
        this.publicityTitle = publicityTitle;
    }

    public String getPublicityIntro() {
        return publicityIntro;
    }

    public void setPublicityIntro(String publicityIntro) {
        this.publicityIntro = publicityIntro;
    }

    public Byte getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Byte getContentType() {
        return contentType;
    }

    public void setContentType(Byte contentType) {
        this.contentType = contentType;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Integer getTotalReadCount() {
        return totalReadCount;
    }

    public void setTotalReadCount(Integer totalReadCount) {
        this.totalReadCount = totalReadCount;
    }

    public Byte getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(Byte isEnded) {
        this.isEnded = isEnded;
    }

    public String getValidAreaCode() {
        return validAreaCode;
    }

    public void setValidAreaCode(String validAreaCode) {
        this.validAreaCode = validAreaCode;
    }

    public Byte getIsContainPhone() {
        return isContainPhone;
    }

    public void setIsContainPhone(Byte isContainPhone) {
        this.isContainPhone = isContainPhone;
    }

    public Integer getWebUserId() {
        return webUserId;
    }

    public void setWebUserId(Integer webUserId) {
        this.webUserId = webUserId;
    }

    public String getToUserType() {
        return toUserType;
    }

    public void setToUserType(String toUserType) {
        this.toUserType = toUserType;
    }

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getPublicityType() {
        return publicityType;
    }

    public void setPublicityType(Byte publicityType) {
        this.publicityType = publicityType;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    @Override
    public String toString() {
        return "TPublicity{" +
                "id=" + id +
                ", firstImageUrl='" + firstImageUrl + '\'' +
                ", publicityTitle='" + publicityTitle + '\'' +
                ", publicityIntro='" + publicityIntro + '\'' +
                ", publicityType=" + publicityType +
                ", checkStatus=" + checkStatus +
                ", checkResult='" + checkResult + '\'' +
                ", contentType=" + contentType +
                ", qrcodeUrl='" + qrcodeUrl + '\'' +
                ", fee='" + fee + '\'' +
                ", totalReadCount=" + totalReadCount +
                ", isEnded=" + isEnded +
                ", validAreaCode='" + validAreaCode + '\'' +
                ", isContainPhone=" + isContainPhone +
                ", webUserId=" + webUserId +
                ", toUserType='" + toUserType + '\'' +
                ", staticUrl='" + staticUrl + '\'' +
                ", logicalDel=" + logicalDel +
                ", createBy=" + createBy +
                ", createName='" + createName + '\'' +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateName='" + updateName + '\'' +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", contentList=" + contentList +
                '}';
    }

    /**
     * 正文数据
     */
    public static class DraftContent {
        private String contentText;
        private String contentImage;

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public String getContentImage() {
            return contentImage;
        }

        public void setContentImage(String contentImage) {
            this.contentImage = contentImage;
        }
    }
}