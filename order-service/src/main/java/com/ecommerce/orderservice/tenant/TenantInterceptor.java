package com.ecommerce.orderservice.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Tenant-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId = request.getHeader(TENANT_HEADER);

        if (tenantId == null || tenantId.trim().isEmpty()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write("Missing or invalid 'X-Tenant-ID' header.");
            return false;
        }

        TenantContext.setTenantId(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Crucial: Clear ThreadLocal to prevent data leakage across pooled servlet threads
        TenantContext.clear();
    }
}