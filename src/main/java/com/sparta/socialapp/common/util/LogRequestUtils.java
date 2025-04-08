package com.sparta.socialapp.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class LogRequestUtils {

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) return "unknown";

        if (userAgent.contains("Mobile")) {
            return "mobile";
        } else {
            return "web";
        }
    }

    public static String getPageUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
