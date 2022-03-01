package com.cmj.park.security.handler;


import com.cmj.park.constant.RespCode;
import com.cmj.park.domain.vo.AjaxResult;
import com.cmj.park.util.ServletUtil;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义403异常处理
 */
@Service
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        int code = RespCode.FORBIDDEN;
        String msg = "没有浏览该内容的权限";
        ServletUtil.renderString(response, new JSONObject(AjaxResult.error(code, msg)).toString());
    }

}
