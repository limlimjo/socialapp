package com.sparta.socialapp.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUserEmailOrGuest() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "비회원";
        } else {
            Object principal = authentication.getPrincipal();
            return (String) principal;
        }
    }
}
