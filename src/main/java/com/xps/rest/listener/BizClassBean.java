package com.xps.rest.listener;

import com.xps.rest.ws.handler.ProtocolType;

/**
 * Created by xiongps on 2018/11/15.
 */
public class BizClassBean {

    private Class<?> clazz;

    private ProtocolType[] protocolTypes;

    private byte pn;

    private String name;

    private String scope;

    public BizClassBean(String name,String scope,Class<?> clazz,ProtocolType[] protocolTypes){
        this.name = name;
        this.scope = scope;
        this.clazz = clazz;
        this.protocolTypes = protocolTypes;
        setPn(protocolsToPn(protocolTypes));
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ProtocolType[] getProtocolTypes() {
        return protocolTypes;
    }

    public void setProtocolTypes(ProtocolType[] protocolTypes) {
        this.protocolTypes = protocolTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public byte getPn() {
        return pn;
    }

    public void setPn(byte pn) {
        this.pn = pn;
    }

    private byte protocolsToPn(ProtocolType []types) {
        byte a=0;
        for(ProtocolType type :types) {
            a+=type.getPn();
        }
        return a;
    }
}
