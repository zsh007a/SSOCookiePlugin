
package com.zsh.kisso.web.waf;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.zsh.kisso.common.util.HttpUtil;
import com.zsh.kisso.web.waf.request.WafRequestWrapper;

/**
 * Waf防火墙过滤器
 * <p>
 * @author zhaoshihao
 * @since 2014-5-8
 */
public class WafFilter implements Filter {

    private static final Logger logger = Logger.getLogger("WafFilter");

    private static String OVER_URL = null;//非过滤地址

    private static boolean FILTER_XSS = true;//开启XSS脚本过滤

    private static boolean FILTER_SQL = true;//开启SQL注入过滤


    public void init(FilterConfig config) throws ServletException {
        //读取Web.xml配置地址
        OVER_URL = config.getInitParameter("over.url");

        FILTER_XSS = getParamConfig(config.getInitParameter("filter_xss"));
        FILTER_SQL = getParamConfig(config.getInitParameter("filter_sql_injection"));
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        // HttpServletResponse res = (HttpServletResponse) response;

        boolean isOver = HttpUtil.inContainURL(req, OVER_URL);

        /** 非拦截URL、直接通过. */
        if (!isOver) {
            try {
                //Request请求XSS过滤
                chain.doFilter(new WafRequestWrapper(req, FILTER_XSS, FILTER_SQL), response);
            } catch (Exception e) {
                logger.severe(" wafxx.jar WafFilter exception , requestURL: " + req.getRequestURL());
            }
            return;
        }

        chain.doFilter(request, response);
    }


    public void destroy() {
        logger.warning(" WafFilter destroy .");
    }


    /**
     * @Description 获取参数配置
     * @param value
     *            配置参数
     * @return 未配置返回 True
     */
    private boolean getParamConfig(String value) {
        if (value == null || "".equals(value.trim())) {
            //未配置默认 True
            return true;
        }
        return new Boolean(value);
    }
}
