package com.xps.rest.ws.handler;

/**
 * Created by xiongps on 2018/11/15.
 */
public enum ProtocolType {

    JSON((byte)1),
    XML((byte)2),
    XJ((byte)4);

    private byte pn;//输入协议

    ProtocolType(byte pn) {
        this.pn = pn;
    }

    public byte getPn() {
        return pn;
    }

}
