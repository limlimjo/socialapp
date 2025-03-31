package com.sparta.socialapp.common.util;

// 문자열 관련 유틸리티 메서드
public class StringUtils {

    // 자르기 유틸 메서드
    public static String truncateText(String text, int maxLength) {

        if (text == null) {
            return "";
        }
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }
}
