package com.htcs.kehaoduo.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.htcs.kehaoduo.common.bean.ReturnStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by htcs on 2017/7/25.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ServletException.class, SQLException.class, RuntimeException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus handleRuntimeException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getClass().toString() + ":" + ex.getMessage();
        logger.error("系统出错了... {}", message);
        return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "系统出错，请稍后重试或者联系管理员！");
    }

    @ExceptionHandler({DuplicateKeyException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus handleSqlException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getClass().toString() + ":" + ex.getMessage();
        logger.error("系统出错了... {}", message);
        return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, "该数据已经存在，请勿重复提交！");
    }


    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus handleNullPointerException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        logger.error("系统出错了... " + ex.getClass().toString() + " :{}", message);
        return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, message == null || "null".equals(message) ? "系统出错，请稍后重试或者联系管理员！" : message);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus handleIllegalArgumentException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        logger.error("系统出错了... " + ex.getClass().toString() + " :{}", message);
        return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler({ReturnStatusException.class})
    @ResponseBody
    public ReturnStatus handleReturnStatusException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        logger.error("系统出错了... {}", message);
        response.setStatus(((ReturnStatusException) ex).getReturnStatusCode().value());
        return new ReturnStatus(((ReturnStatusException) ex).getReturnStatusCode(), message);
    }

    @ExceptionHandler({SignatureException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus handleSignatureException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        logger.error("系统出错了... {}", message);
        return new ReturnStatus(ReturnStatus.StatusCode.UNAUTHORIZED, "签名校验错误，请重新获取！");
    }

    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus expiredJwtException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        logger.error("系统出错了... {}", message);
        return new ReturnStatus(ReturnStatus.StatusCode.UNAUTHORIZED, "登录已经过期，请重新登录！");
    }

    @ExceptionHandler({JwtException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus handleJwtException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        logger.error("系统出错了... {}", message);
        return new ReturnStatus(ReturnStatus.StatusCode.UNAUTHORIZED, "签名错误，请重新获取签名！");
    }

    @ExceptionHandler({PageErrorException.class})
    @ResponseStatus
    public void handlePageErrorException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        String message = ex.getMessage();
        logger.error("系统出错了... {}", message);
        response.sendRedirect("/error/500.html");
    }

    @ExceptionHandler({WxErrorException.class})
    @ResponseStatus
    @ResponseBody
    public ReturnStatus handleWxErrorException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        String message = ex.getMessage();
        logger.error("系统出错了... {}", message);
        JSONObject jsonObject = JSONObject.parseObject(message, JSONObject.class);
        Integer errcode = jsonObject.getInteger("errcode");
        String errMsg = "系统出错，请稍后重试或者联系管理员！";
        if (errcode.equals(40163)) {
            errMsg = "请勿刷新该页面！如要刷新页面，请尝试关闭该页面后重新进入！";
        }
        return new ReturnStatus(ReturnStatus.StatusCode.INTERNAL_SERVER_ERROR, errMsg);
    }
}