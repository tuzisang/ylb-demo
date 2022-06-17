package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000120697553";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFHCyzDDjZlsHIKptipzf1511HO0uFzCLhEC7/ebqShRIGZ8XldoLMjTqf0DcL4cFZfFVyxb9rls+MxRvcF/CQRefi3PRp/eq5Lge52+7T6hegQ1qNFUHQv41OBSoEh3zGcjNJeT3Du1/LKP57ObE2+Mt/JJFVzu5mPeq7cpzL89jfnscsPWMZXFrkzZQ5YejHeX7iCW7+6SOBcyuvhnpywoYWv8EJclJAgc7+F8dWTvF5EJNBxiy0VKj8KkM5QMwF3elo1IIddVnH/y7f49VSgqKu8GHMPwpaAtA36cqBuvM03kmhBVA7TN8uTVLlM+9iatujeu5M52IJRW3rsvk5AgMBAAECggEBAII/CmH8YJhQkrcJMdg7ZV+Bj11G4OQ/1hVBX6tt8iiyeuu3mQWpi90ZiDaeZ26B7U5hgb73ThG5lMbajCM1bZfOP1NV/vbDlWyzZK3xTWUgVxPjM/R+y1K2TE7+cJnT9Ty8vAB12QDwFJDXQQNUdVoj4gM7xdYf0FhM16WpRVGujkNiFvM0s2KY6X592TpdR3AWIrLzaj0NOtWp5tMWCpu0ZGmZvzqxhj4BfyBJT1AK2Vf8nxiEF/4NUYX7Qm4+rufdO4yjik1eL2mVukK3gC3//f5R/yy71zmN4qCxB2EJXXaldahiXNIpkwQwJZeyQN0cR0Zhge0aN0eSgefOYx0CgYEA30kwIppC649DSE+a7s8jEDuf4Qe9og0W2lpjVLHuxMJ396VOSNPwiUNcgg5gdUkh8qMOwEZUEQ4B55maqwGicVClR3yvalgSWISKxBhjk5KQyhfGgWZzDlNcihO7J0oLrW5Xp2RoNsfuoaSCilFlzbH9fErOJSYZliQNIofqdMsCgYEAmJzAEF8CSPqInGNCcqq4Mqdt1dlozmZdQiis/GudZUoIvhe1a15lEwgkFBP9iOe7wS7Wx/7WlzAe3joAlKuXjBd6Sd+pS3Tz0IBNK+4kKwFCJ5vCLSV7/9Y6qa1xc+QjtBm7rN5I5tQ58ZX+yQJWpoQhbPTs/jxdUncXXrK9zYsCgYBWWBf8GYktTE4pZVc1Z/ZYLaZqv7KOOt+OeouU0eXSQp1fr5XxRbvraJgaVvLoB+rPyExJACk8Gx2TcOvFmzE0/ombdlTjG8GkZ0S+Bexjwbap373MhlCRxpyvbGfgDpsratMMkSBXpsLZ0SMF1+o1O9IZQVgsN2qHi9NkegipNwKBgBu5jmLq/71GVn8iwTWXsjLxm9CX2KR2kEauoQ6SdluqYYA3SmwhD/evKO7E2vB+OOvVmItozo9U3Ka+ntBjg/qdYUf+bAEbVBfy1xI+BzAQ4HXVpCcTgj1uK+YbblsBfUMA0cmL8G6O65/qAgl+dUN3CaODs3JUgRe0LvizPCwVAoGBAKrtYs/9YqXEeXcDg0JM6bDjWNaUODwbB6gY8MQ6ZCLyrFJ7CA3IRIlNDJpytjZH8lt1tQoMpzaEAJV0RrVB2kVdlspXvuAbj2yo5vXWHiX4znTlkYCFsN1rpRRJGFbVjNVygYapQZuf2BHH8f6Tsc4LNQhcPQIa9ai8rARumQyo";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoxNnkPLZHFTsh/mXRmAVV/vcgrhegt6+dGCL4Zxm2o34YsrMADiSGFdRTbQ13CXSz8q3iRuo6IcNulPr8+K+h+9BG0sFV0zBztRFzHo2b/8rDXlycRmWc1/swPY4ARlopQT+4umbQsJ+mtqDBf8A4Oa9ZBksYpn9jJu3q2O011G9DnHNjiHhM0GSLXYlVvsLxpewitPOa1fOEAPT3LgiH5aCB0BjnLjuuqAlCFDZLPWB5Ylr63gA8rHQEWIPdpPhbLniym0yp3sub98a3DfnFU58hfUSPVdzU9p3aMdxYaCb7VCB1qY7UAlTusY9LEYWcKolu2BWSw/bYX2W8sfNywIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8083/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8083/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝沙箱网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

