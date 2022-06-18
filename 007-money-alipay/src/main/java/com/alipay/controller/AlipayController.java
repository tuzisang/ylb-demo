package com.alipay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.config.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author tzsang
 * @create 2022-06-17 15:52
 */
@Controller
public class AlipayController {

    @RequestMapping("/tradePay")
    public void tradePay(String out_trade_no, String total_amount, String subject,
                         Model model, HttpServletResponse response, HttpSession session) throws AlipayApiException, IOException {
        Object alipay = session.getAttribute("alipay");

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        model.addAttribute("result", result);
        //输出
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(result);
    }

    //支付后的回调页面
    @RequestMapping("/payReturn")
    public String payReturn(String out_trade_no,
                            HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException, AlipayApiException {
        //拼装所有的支付宝返回的参数，进行“验证签名”
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        String msg = "";
        if (signVerified==false) { //验签失败
            msg = "1";
        }

        //验签正确，才能信任这些参数
        //调用支付宝的查询功能，检查订单是否支付成功
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //分析查询后的JSON结果
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject responseObject = jsonObject.getJSONObject("alipay_trade_query_response");
        if (!"10000".equals(responseObject.getString("code"))) {
            msg = "2";
        }
        String tradeStatus = responseObject.getString("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            msg = "3";
        } else if ("TRADE_CLOSED".equals(tradeStatus)) {
            msg = "4";
        } else {
            msg = "5";
        }
        System.out.println("进来了！！！！！");
        msg = new String(msg.getBytes("iso8859-1"),"utf-8");
        return "redirect:http://localhost:8081/money/loan/page/payResult?trade_msg="
                + msg;
    }

    @GetMapping("/toPayResult")
    public String toPayResult() {

        System.out.println("进来了");

        return "payResult";
    }

    //根据商家订单号，查询支付结果
    @RequestMapping("/tradeQuery")
    @ResponseBody
    public String tradeQuery(String out_trade_no) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        //输出
        return result;
    }
}
