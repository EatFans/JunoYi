package com.junoyi.wechat.pay;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.service.WxPayService;
import com.junoyi.platform.domain.PayRequest;
import com.junoyi.platform.domain.PayResponse;
import com.junoyi.platform.enums.Platform;
import com.junoyi.platform.pay.PayProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付提供者
 * <p>
 * 实现微信支付的创建订单功能，支持 JSAPI、APP、H5、NATIVE 等多种支付方式
 * </p>
 *
 * @author Fan
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnBean(WxPayService.class)
public class WeChatPayProvider implements PayProvider {

    private final WxPayService wxPayService;

    /**
     * 获取平台类型
     *
     * @return 微信平台
     */
    @Override
    public Platform getPlatform() {
        return Platform.WECHAT;
    }

    /**
     * 创建支付订单
     *
     * @param request 支付请求对象
     * @return 支付响应对象
     */
    @Override
    public PayResponse createOrder(PayRequest request) {
        try {
            // 构建微信支付请求
            WxPayUnifiedOrderV3Request wxRequest = buildWxPayRequest(request);

            // 调用微信支付接口
//            WxPayUnifiedOrderV3Result wxResult = wxPayService.unifiedOrderV3(wxRequest);

//            log.info("微信支付订单创建成功，商户订单号: {}, 预支付ID: {}",
//                    request.getOutTradeNo(), wxResult.getPrepayId());

            // 构建响应对象
//            return buildPayResponse(request, wxResult);

            return null;

        } catch (Exception e) {
            log.error("微信支付订单创建失败: {}", e.getMessage(), e);
            return PayResponse.fail("创建支付订单失败: " + e.getMessage());
        }
    }

    /**
     * 构建微信支付请求
     *
     * @param request 统一支付请求
     * @return 微信支付请求对象
     */
    private WxPayUnifiedOrderV3Request buildWxPayRequest(PayRequest request) {
        WxPayUnifiedOrderV3Request wxRequest = new WxPayUnifiedOrderV3Request();

        // 设置商户订单号
        wxRequest.setOutTradeNo(request.getOutTradeNo());

        // 设置商品描述
        wxRequest.setDescription(request.getDescription());

        // 设置订单金额（转换为分）
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(request.getAmount().multiply(new java.math.BigDecimal("100")).intValue());
        amount.setCurrency(request.getCurrency());
        wxRequest.setAmount(amount);

        // 设置支付者信息（JSAPI 支付必填）
        if ("JSAPI".equals(request.getTradeType()) && request.getUserId() != null) {
            WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
            payer.setOpenid(request.getUserId());
            wxRequest.setPayer(payer);
        }

        // 设置回调通知地址
        wxRequest.setNotifyUrl(request.getNotifyUrl());

        // 设置附加数据
        wxRequest.setAttach(request.getAttach());

        // 设置订单失效时间
        if (request.getTimeExpire() != null) {
            wxRequest.setTimeExpire(request.getTimeExpire());
        }

        return wxRequest;
    }

    /**
     * 构建支付响应对象
     *
     * @param request  支付请求
     * @param wxResult 微信支付结果
     * @return 支付响应对象
     */
    private PayResponse buildPayResponse(PayRequest request, WxPayUnifiedOrderV3Result wxResult) {
        Map<String, Object> payInfo = new HashMap<>();

        // 根据不同的支付方式返回不同的支付信息
        String tradeType = request.getTradeType();

        if ("JSAPI".equals(tradeType)) {
            // JSAPI 支付返回调起支付所需参数

        } else if ("NATIVE".equals(tradeType)) {
            // NATIVE 支付返回二维码链接
            payInfo.put("codeUrl", wxResult.getCodeUrl());
        } else if ("H5".equals(tradeType)) {
            // H5 支付返回支付链接
            payInfo.put("h5Url", wxResult.getH5Url());
        }

        return PayResponse.builder()
                .code("SUCCESS")
                .message("创建订单成功")
                .outTradeNo(request.getOutTradeNo())
                .prepayId(wxResult.getPrepayId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .payInfo(payInfo)
                .codeUrl(wxResult.getCodeUrl())
                .h5Url(wxResult.getH5Url())
                .build();
    }
}

