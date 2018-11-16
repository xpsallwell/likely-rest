package com.xps.rest.ws.http;

import com.xps.rest.exceptions.BusinessException;
import com.xps.rest.exceptions.DefaultExceptionComponent;
import com.xps.rest.exceptions.IAcctException;
import com.xps.rest.util.ProtocolUtil;
import com.xps.rest.ws.handler.BizzBeanFactory;
import com.xps.rest.ws.handler.ProcessService;
import com.xps.rest.ws.handler.ProtocolContent;
import com.xps.rest.ws.handler.ProtocolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by xiongps on 2018/11/15.
 */
public abstract class BaseService {

    protected static Logger logger = LoggerFactory.getLogger(CustomerService.class);


    protected String handler(String method, String content){
        long beginTime = System.currentTimeMillis();
        String returnStr;
        ProtocolContent protocolContent = new ProtocolContent();
        try {
            this.handlerProtocolContent(protocolContent,method,content);
            ProcessService processService = BizzBeanFactory.getBizzServiceBean(
                    getMethodScope(),protocolContent.getMethodName(),protocolContent.getProtocolType());
            if(processService == null) {
                throw new BusinessException(DefaultExceptionComponent.NOT_EXISTS,
                        "请求的接口服务["+getMethodScope()+"/"+protocolContent.getMethodName()+"]不存在");
            }
            returnStr = processService.process(protocolContent);//1.解析校验协议报文，2.取需要的参数并校验参数是否合法，
        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            returnStr = this.handlerException(e,protocolContent);
        }
        long endTime = System.currentTimeMillis();
        this.handlerLog(protocolContent,returnStr,endTime - beginTime);
        return returnStr;
    }

    protected void handlerProtocolContent(ProtocolContent pc,String method, String content){
        pc.parseMethod(method);
        pc.parseContent(content);
    }

    protected String handlerException(Exception e,ProtocolContent protocolContent){

        if(e instanceof BusinessException){
            BusinessException be = (BusinessException)e;
            IAcctException ie = be.getEnumException();
            String code = ie==null?(be.getCode() ==null?"0001":be.getCode()):ie.getCode();
            return ProtocolUtil.getExceptionRet(protocolContent, code, e.getMessage());
        }
        logger.error(Arrays.toString(e.getStackTrace()));
        return ProtocolUtil.getExceptionRet(protocolContent, "9999", "接口服务处理业务时出现错误!");
    }

    protected void handlerLog(ProtocolContent protocolContent,String returnStr,long dealTime){
        logger.info(" 当前调用接口名称为：["+protocolContent.getMethodName()+"],处理所花费的时间为：["+dealTime+"]毫秒----");
        logger.info("--"+protocolContent.getMethodName()+" out XML:"+returnStr);
    }

    protected String getMethodScope(){
        return this.getClass().getSimpleName();
    }

}
