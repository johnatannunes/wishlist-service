package com.appstack.wishlist.infrastructure.filter;

import com.appstack.wishlist.common.logging.Logging;
import com.appstack.wishlist.config.MDCKey;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestIdFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String requestId = UUID.randomUUID().toString();
            MDC.put(MDCKey.REQUEST_ID.getKey(), requestId);
            if (servletResponse instanceof HttpServletResponse httpServletResponse) {
                httpServletResponse.setHeader("X-Request-Id", requestId);
            }

            Logging.logger(logger).mdcKey(MDCKey.REQUEST_ID)
                    .info("Starting process");

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
