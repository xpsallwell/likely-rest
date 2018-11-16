/*
 * Project Name: tao-aop
 * File Name: QryCheckUserSign.java
 * Package Name:com.yaoyaohao.tao.ws.http.impl
 * Date:2015-9-16上午8:55:01
 * Copyright (c) 2015, xiongps All Rights Reserved.
 * 药药好（杭州）网络科技有限公司
*/
package com.xps.rest.ws.http.impl;

import com.alibaba.fastjson.JSONObject;

import com.xps.rest.bean.StaffBean;
import com.xps.rest.entity.user.SysStaff;
import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import com.xps.rest.listener.BizBeanService;
import com.xps.rest.service.user.IStaffInfoMgrService;
import com.xps.rest.util.JsonUtil;
import com.xps.rest.util.ProtocolUtil;
import com.xps.rest.util.XmlUtils;
import com.xps.rest.ws.handler.AbstractProcessService;
import com.xps.rest.ws.handler.ProtocolContent;
import com.xps.rest.ws.handler.ProtocolType;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户签到或查询签到信息
 * @author xiongps
 * @date 2017-07-21 16:35:23
 */
@Service
@BizBeanService(name = "queryStaff",scope = "CustomerService",protocol = {ProtocolType.JSON,ProtocolType.XML})
public class QryStaffInfo extends AbstractProcessService<StaffBean> {

    @Autowired
    private IStaffInfoMgrService staffInfoMgrService;

    @Override
    protected StaffBean getClientParameterBean(JSONObject jsonObject) throws Exception {
        StaffBean bean = new StaffBean();
        JSONObject body = jsonObject.getJSONObject("body");
        bean.setLoginAccount(JsonUtil.getAttrText(body,"account",true));
        return bean;
    }

    @Override
    protected StaffBean getClientParameterBean(Document document) throws Exception {
        StaffBean bean = new StaffBean();
        bean.setLoginAccount(XmlUtils.getNodeText(document,"//body/account",true));
        return bean;
    }

    @Override
    protected ProtocolContent comboBiz(ProtocolContent protocolContent, StaffBean bean) throws Exception {
        JSONObject map = new JSONObject();

        SysStaff staff = staffInfoMgrService.querySysStaffByAccount(bean.getLoginAccount());
        if(staff==null) {
            throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,"用户不存在");
        }
        map.put("staffId",staff.getStaffId());
        map.put("realName",staff.getRealName());
        map.put("homeAddress",staff.getHomeAddress());

        return ProtocolUtil.getReturnContent(protocolContent,map);
    }
}
