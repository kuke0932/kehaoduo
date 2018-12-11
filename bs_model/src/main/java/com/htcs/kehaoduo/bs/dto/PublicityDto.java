package com.htcs.kehaoduo.bs.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhaokaiyuan
 * @create 2017-11-21 9:57
 **/
public class PublicityDto implements Serializable {
    private Integer id;
    private boolean inValidAreaCode;
    private boolean userHasRead;
    private String publicityIntro;
    private String publicityTitle;
    private String content;
    private String firstImageUrl;
    private Date createTime;
    private String validAreaCode;
    private Integer readCount;
    private Byte logicalDel;
    private BigDecimal bonus;
    private Byte isEnded;
    private String readerOpenid;
    private Byte isContainPhone;
    private Byte publicityType;
    private Byte contentType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getInValidAreaCode() {
        return inValidAreaCode;
    }

    public void setInValidAreaCode(boolean inValidAreaCode) {
        this.inValidAreaCode = inValidAreaCode;
    }

    public boolean getUserHasRead() {
        return userHasRead;
    }

    public void setUserHasRead(boolean userHasRead) {
        this.userHasRead = userHasRead;
    }

    public String getPublicityIntro() {
        return publicityIntro;
    }

    public void setPublicityIntro(String publicityIntro) {
        this.publicityIntro = publicityIntro;
    }

    public String getPublicityTitle() {
        return publicityTitle;
    }

    public void setPublicityTitle(String publicityTitle) {
        this.publicityTitle = publicityTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstImageUrl() {
        return firstImageUrl;
    }

    public void setFirstImageUrl(String firstImageUrl) {
        this.firstImageUrl = firstImageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getValidAreaCode() {
        return validAreaCode;
    }

    public void setValidAreaCode(String validAreaCode) {
        this.validAreaCode = validAreaCode;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Byte getLogicalDel() {
        return logicalDel;
    }

    public void setLogicalDel(Byte logicalDel) {
        this.logicalDel = logicalDel;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public Byte getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(Byte isEnded) {
        this.isEnded = isEnded;
    }

    public String getReaderOpenid() {
        return readerOpenid;
    }

    public void setReaderOpenid(String readerOpenid) {
        this.readerOpenid = readerOpenid;
    }

    public Byte getIsContainPhone() {
        return isContainPhone;
    }

    public void setIsContainPhone(Byte isContainPhone) {
        this.isContainPhone = isContainPhone;
    }

    public Byte getPublicityType() {
        return publicityType;
    }

    public void setPublicityType(Byte publicityType) {
        this.publicityType = publicityType;
    }

    public Byte getContentType() {
        return contentType;
    }

    public void setContentType(Byte contentType) {
        this.contentType = contentType;
    }
}
