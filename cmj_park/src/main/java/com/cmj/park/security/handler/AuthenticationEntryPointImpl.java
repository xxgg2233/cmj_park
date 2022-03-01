package com.cmj.park.security.handler;

import com.cmj.park.constant.RespCode;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.util.ServletUtil;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类 返回未授权
 *
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        int code = RespCode.UNAUTHORIZED;
        String msg = String.format("请求访问：%s，认证失败", request.getRequestURI());
        ServletUtil.renderString(response, new JSONObject(AjaxResult.error(code, msg)).toString());
    }
}
