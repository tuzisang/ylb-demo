package com.kpay.money.cotroller;

import com.kpay.money.config.BillConfig;
import com.kpay.money.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.kpay.money.util.Pkipair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tzsang
 * @create 2022-06-18 17:16
 */
@Controller
public class KpayController {
    @Autowired
    private Pkipair pki;

    //订单提交界面
    @GetMapping("/toPay")
    public String toPay(String orderTime, String orderId, String orderAmount, Model model){
        model.addAttribute("orderTime", orderTime);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderAmount", orderAmount);
        return "toPay";
    }

    //支付入口，生成自提交表单封装块钱支付所需参数
    @PostMapping("/pay")
    public String pay(String orderTime, String orderId, String orderAmount, Model model){
        //1 拼接参数，并调用 pki 加密签名信息
        //1.1 拼接参数
        String signMsgVal = "";
        signMsgVal = ParamUtil.appendParam(signMsgVal, "inputCharset", BillConfig.inputCharset);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "pageUrl", BillConfig.pageUrl);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "bgUrl", BillConfig.bgUrl);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "version", BillConfig.version);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "language", BillConfig.language);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "signType", BillConfig.signType);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "merchantAcctId", BillConfig.merchantAcctId);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "orderId", orderId);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "orderAmount", orderAmount);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "orderTime", orderTime);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "payType", BillConfig.payType);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "bankId", BillConfig.bankId);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "redoFlag", BillConfig.redoFlag);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "pid", BillConfig.pid);
        //1.2 对参数加密签名
        String signMsg = pki.signMsg(signMsgVal);
        //2 把所有参数和加密签名信息封装到 model，用于 推送到 pay.html 的表单
        //2.1 加密签名信息
        model.addAttribute("signMsg", signMsg);
        //2.2 其他参数
        model.addAttribute("merchantAcctId", BillConfig.merchantAcctId);
        model.addAttribute("inputCharset", BillConfig.inputCharset);
        model.addAttribute("pageUrl", BillConfig.pageUrl);
        model.addAttribute("bgUrl", BillConfig.bgUrl);
        model.addAttribute("version", BillConfig.version);
        model.addAttribute("language", BillConfig.language);
        model.addAttribute("signType", BillConfig.signType);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderAmount",orderAmount);
        model.addAttribute("orderTime", orderTime);
        model.addAttribute("payType", BillConfig.payType);
        model.addAttribute("bankId", BillConfig.bankId);
        model.addAttribute("redoFlag", BillConfig.redoFlag);
        model.addAttribute("pid", BillConfig.pid);

        //3 返回视图，结合thymeleaf页面生成自动提交表单
        return "pay";
    }

    @RequestMapping("/payReturn")
    public String payReturn(String merchantAcctId,	//1 获取参数
                            String version,
                            String language,
                            String signType,
                            String payType,
                            String bankId,
                            String orderId,
                            String orderTime,
                            String orderAmount,
                            String bindCard,
                            String bindMobile,
                            String bankDealId,
                            String dealTime,
                            String payAmount,
                            String ext1,
                            String ext2,
                            String payResult,
                            String errCode,
                            String signMsg,
                            String dealId,
                            String fee,
                            String aggregatePay,
                            Model model) {
        //2 参数拼接
        String merchantSignMsgVal = "";
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal,"merchantAcctId", merchantAcctId);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "version",version);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "language",language);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "signType",signType);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "payType",payType);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "bankId",bankId);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "orderId",orderId);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "orderTime",orderTime);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "orderAmount",orderAmount);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "bindCard",bindCard);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "bindMobile",bindMobile);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "dealId",dealId);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "bankDealId",bankDealId);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "dealTime",dealTime);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "payAmount",payAmount);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "fee", fee);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "ext1", ext1);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "ext2", ext2);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "payResult",payResult);
        merchantSignMsgVal = ParamUtil.appendParam(merchantSignMsgVal, "errCode",errCode);
        //3 验证签名
        boolean flag = pki.enCodeByCer(merchantSignMsgVal, signMsg);
        String msg = null;
        if(flag){	//验签成功提取结果
            switch (Integer.valueOf(payResult)){
                case 10:
                    //支付成功，后续业务处理
                    //....
                    msg = "3";
                    break;
                default:
                    msg = "4";
                    break;
            }
        }else{		//验签失败
           msg = "4";
        }
        return "redirect:http://localhost:8081/money/loan/page/payResult?trade_msg="
                + msg;
    }

    @GetMapping("/tradeQuery")
    public String tradeQuery(String orderId, Model model){
        //1 拼接参数，并调用 pki 加密签名信息
        //1.1 拼接参数
        String signMsgVal = "";

        String key = "D4TSSG89AX2A596A";//XIXMFISFG7RGDKQN

        signMsgVal = ParamUtil.appendParam(signMsgVal,"version", BillConfig.version);
        signMsgVal = ParamUtil.appendParam(signMsgVal,"signType", BillConfig.signType);
        signMsgVal = ParamUtil.appendParam(signMsgVal,"merchantAcctId", BillConfig.merchantAcctId);
        signMsgVal = ParamUtil.appendParam(signMsgVal,"queryType", BillConfig.queryType);
        signMsgVal = ParamUtil.appendParam(signMsgVal,"queryMode", BillConfig.queryMode);
        signMsgVal = ParamUtil.appendParam(signMsgVal, "orderId", orderId);
        signMsgVal = ParamUtil.appendParam(signMsgVal,"inputCharset", BillConfig.inputCharset);
        signMsgVal = ParamUtil.appendParam(signMsgVal,"key", key);
        //1.2 对参数加密签名
        String signMsg = pki.signMsg(signMsgVal);
        //2 把所有参数和加密签名信息封装到 model，用于 推送到 pay.html 的表单
        //2.1 加密签名信息
        model.addAttribute("version", BillConfig.version);
        model.addAttribute("signType", BillConfig.signType);
        model.addAttribute("merchantAcctId", BillConfig.merchantAcctId);
        model.addAttribute("queryType", BillConfig.queryType);
        model.addAttribute("queryMode", BillConfig.queryMode);
        model.addAttribute("orderId", orderId);
        model.addAttribute("signMsg", signMsg);
        model.addAttribute("inputCharset",  BillConfig.inputCharset);
        model.addAttribute("key",  key);

        return "tradeQuery";

    }
    
}
