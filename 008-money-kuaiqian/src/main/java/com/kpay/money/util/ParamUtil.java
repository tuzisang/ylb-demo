package com.kpay.money.util;

/**
 * @author tzsang
 * @create 2022-06-18 17:06
 */
public class ParamUtil {
    /** 工具方法，把参数拼接到一个字符串中返回
     * @param 拼接前的参数字符串
     * @param 参数名
     * @param 参数值
     * @return 拼接后的参数字符串
     */
    public static String appendParam(String returns, String paramId, String paramValue) {
        if (returns != "") {
            if (paramValue != "") {
                returns += "&" + paramId + "=" + paramValue;
            }
        } else {
            if (paramValue != "") {
                returns = paramId + "=" + paramValue;
            }
        }
        return returns;
    }
}