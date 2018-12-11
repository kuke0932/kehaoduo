package com.htcs.kehaoduo.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by htcs on 2017/7/25.
 * 处理过程中的异常捕获，不包括请求异常和返回异常
 */
@Component
public class ErrorHandlerExceptionResolver implements HandlerExceptionResolver {

    Logger logger = LoggerFactory.getLogger(ErrorHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ReturnStatus returnStatus = new ReturnStatus();

        returnStatus.setStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR);
        logger.error("系统出错： {} {}", e.getClass().toString(), e.getMessage());
        returnStatus.setMessage("系统出错，请联系管理员或稍后重试！");
        httpServletResponse.setStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR.getCode());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = httpServletResponse.getWriter();
        } catch (IOException iOException) {
            iOException.printStackTrace();
            logger.error("IOException[getWriter]出错: {}", iOException.getMessage());
        }
        writer.write(JSONObject.toJSONString(returnStatus));
        writer.close();

        return new ModelAndView();
    }
}
