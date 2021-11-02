package com.chinamobile.auth.interceptor;


import com.chinamobile.auth.annotation.JwtIgnore;
import com.chinamobile.auth.bean.Audience;
import com.chinamobile.auth.exception.CustomException;
import com.chinamobile.auth.util.JwtTokenUtil;
import com.chinamobile.auth.util.ResultCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ========================
 * token验证拦截器
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/7/18 9:46
 * Version: v1.0
 * ========================
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private Audience audience;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        String uri = request.getRequestURI();
        /*if(uri.startsWith("/webjars/") || uri.endsWith(".html")){
            return true;
        }*/

        String header = request.getMethod();
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
            if (jwtIgnore != null) {
                return true;
            }
        }

        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 获取请求头信息authorization信息
        final String authHeader = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        log.info("## authHeader= {}", authHeader+", uri="+request.getRequestURL().toString());

        if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            log.info("### 用户未登录，请先登录 ###"+", uri="+request.getRequestURL().toString());
            //throw new CustomException(ResultCode.USER_NOT_LOGGED_IN);
            //throw new CustomException(400, "用户未登录，请先登录");
            //throw new CustomException("用户未登录，请先登录");
            Assert.isTrue(false, "用户未登录，请先登录");
            /*response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try{
                JSONObject json = new JSONObject();
                json.put("success","false");
                json.put("msg","认证失败，未通过拦截器");
                json.put("code","50000");
                response.getWriter().append(json.toJSONString());
                System.out.println("认证失败，未通过拦截器");
                //        response.getWriter().write("50000");
            }catch (Exception e){
                e.printStackTrace();
                response.sendError(500);
                return false;
            }*/
            //return false;
        }

        // 获取token
        //final String token = authHeader.substring(7);
        if(authHeader != null){
            final String token = authHeader.substring(JwtTokenUtil.TOKEN_PREFIX.length());


            if(audience == null){
                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                audience = (Audience) factory.getBean("audience");
            }

            // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
            Claims claims = null;
            try{
                claims = JwtTokenUtil.parseJWT(token, audience.getSecret());
            }catch (SignatureException se) {
                //throw new CustomException(ResultCode.FORBIDDEN.getCode(), "token错误，密钥不匹配");
                Assert.isTrue(false, "token错误，密钥不匹配");
            }catch (ExpiredJwtException ex){
                //throw new CustomException(ResultCode.FORBIDDEN.getCode(), "token已过期，请重新登录");
                Assert.isTrue(false, "token已过期，请重新登录");
            }catch (Exception e){
                //throw new CustomException(ResultCode.FORBIDDEN.getCode(), "token解析异常，请重新登录");
                Assert.isTrue(false, "token解析异常，请重新登录");
            }


        }

        return true;
    }

}
