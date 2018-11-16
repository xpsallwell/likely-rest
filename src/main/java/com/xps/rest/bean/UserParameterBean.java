package com.xps.rest.bean;


import java.util.List;

/**
 * 用户信息bean.
 *
 * @author xiongps
 * @version 2015-9-21 上午8:46:29
 * @since JDK1.7
 */
public class UserParameterBean {

    private String customerCd;

    private String customerId;

    private String userId;

    private String userCd;

    private String userName;

    private String oldPassword;

    private String newPassword;
    private String pwdType;
    private String pswGrade;
    private String operateType;

    private String channel;

    private String loginIp;

    private String merchandiseId;
    private String merchandiseCd;

    private String operationType;

    private String logisticsId;

    private String logisticsCd;

    private String logisticsNm;

    private String labelId;

    private List<String> merchandiseIds;

    private List<String> merchandiseCds;

    private List<String> dicCodes;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCd() {
        return userCd;
    }

    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


    public String getPwdType() {
        return pwdType;
    }

    public void setPwdType(String pwdType) {
        this.pwdType = pwdType;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getMerchandiseId() {
        return merchandiseId;
    }

    public void setMerchandiseId(String merchandiseId) {
        this.merchandiseId = merchandiseId;
    }

    public String getMerchandiseCd() {
        return merchandiseCd;
    }

    public void setMerchandiseCd(String merchandiseCd) {
        this.merchandiseCd = merchandiseCd;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public List<String> getMerchandiseIds() {
        return merchandiseIds;
    }

    public void setMerchandiseIds(List<String> merchandiseIds) {
        this.merchandiseIds = merchandiseIds;
    }

    public List<String> getDicCodes() {
        return dicCodes;
    }

    public void setDicCodes(List<String> dicCodes) {
        this.dicCodes = dicCodes;
    }

    public String getPswGrade() {
        return pswGrade;
    }

    public void setPswGrade(String pswGrade) {
        this.pswGrade = pswGrade;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsCd() {
        return logisticsCd;
    }

    public void setLogisticsCd(String logisticsCd) {
        this.logisticsCd = logisticsCd;
    }

    public String getLogisticsNm() {
        return logisticsNm;
    }

    public void setLogisticsNm(String logisticsNm) {
        this.logisticsNm = logisticsNm;
    }

    public List<String> getMerchandiseCds() {
        return merchandiseCds;
    }

    public void setMerchandiseCds(List<String> merchandiseCds) {
        this.merchandiseCds = merchandiseCds;
    }

    public String getCustomerCd() {
        return customerCd;
    }

    public void setCustomerCd(String customerCd) {
        this.customerCd = customerCd;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
