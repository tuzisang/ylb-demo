package com.kpay.money.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author tzsang
 * @create 2022-06-18 17:04
 */
@Configuration
public class BillConfig {
    //人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
    public static String merchantAcctId = "1001214035601";//
    //编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
    public static String inputCharset = "1";
    //接收支付结果的页面地址，该参数一般置为空即可。
    public static String pageUrl = "http://127.0.0.1:8084/payReturn";
    //服务器接收支付结果的后台地址，该参数务必填写，bgUrl和pageUrl二值不能同时为空。
    public static String bgUrl = "";
    //网关版本，固定值：v2.0,该参数必填。
    public static String version = "v2.0";
    //语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
    public static String language = "1";
    //签名类型,该值为4，代表PKI加密方式,该参数必填。
    public static String signType = "4";
    //支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10-1或10-2，必填。
    public static String payType = "00";
    //银行代码，如果payType为00，该值可以为空；如果payType为10-1或10-2，该值必须填写，具体请参考银行列表。
    public static String bankId = "";
    //同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
    public static String redoFlag = "0";
    //快钱合作伙伴的帐户号，即商户编号，可为空。
    public static String pid = "";
    //固定选择值：0、1 0 按商户订单号单笔查询（只返回成功订单） 1 按交易结束时间批量查询（只返回成功订单）
    public static String queryType = "0";
    //代表简单查询
    public static String queryMode = "1";
}