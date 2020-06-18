package com.viettel.arpu.filter;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @author trungnb3
 * @Date :5/29/2020, Fri
 */
@Component
public class AddRequestIdHeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestId = ((HttpServletRequest) servletRequest).getHeader("requestId");
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest((HttpServletRequest) servletRequest);
        if (Strings.isEmpty(requestId)) {
            mutableRequest.putHeader("requestId", UUID.randomUUID().toString());
        }
        filterChain.doFilter(mutableRequest, servletResponse);
    }
}
