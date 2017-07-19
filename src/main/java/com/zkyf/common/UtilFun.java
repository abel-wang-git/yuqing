package com.zkyf.common;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wanghuiwen on 17-2-23.
 */
public class UtilFun {
    /**
     * 判断list不为空
     *
     * @param list
     * @return 不为空返回true
     */
    public static boolean isEmptyList(List list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断str不为空
     *
     * @param str
     * @return 不为空返回true
     */
    public static boolean isEmptyString(String str) {
        if (str != null && !str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
