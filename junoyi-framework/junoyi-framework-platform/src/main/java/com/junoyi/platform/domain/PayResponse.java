package com.junoyi.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 统一支付响应对象
 * <p>
 * 该类封装了支付订单创建后返回的通用响应信息，适用于微信支付、支付宝支付等多种支付渠道。
 * 不同支付方式返回的具体参数会存放在 payInfo 字段中。
 * </p>
 *
 * @author Fan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayResponse {

    /**
     * 响应状态码
     * <p>
     * SUCCESS：成功
     * FAIL：失败
     * PROCESSING：处理中
     * </p>
     */
    private String code;

    /**
     * 响应消息
     * <p>
     * 返回信息，如非空，为错误原因
     * </p>
     */
    private String message;

    /**
     * 商户订单号
     * <p>
     * 商户系统内部订单号
     * </p>
     */
    private String outTradeNo;

    /**
     * 支付平台订单号
     * <p>
     * 支付平台生成的订单号（微信支付订单号、支付宝交易号等）
     * </p>
     */
    private String transactionId;

    /**
     * 订单金额
     * <p>
     * 订单总金额，单位为元
     * </p>
     */
    private BigDecimal amount;

    /**
     * 货币类型
     * <p>
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * </p>
     */
    private String currency;

    /**
     * 支付信息
     * <p>
     * 根据不同支付方式返回的具体支付参数：
     * - JSAPI：返回 prepay_id 等参数供前端调起支付
     * - APP：返回 APP 调起支付所需参数
     * - H5：返回 h5_url 供跳转
     * - NATIVE：返回 code_url 二维码链接
     * </p>
     */
    private Map<String, Object> payInfo;

    /**
     * 二维码链接
     * <p>
     * 扫码支付时返回的二维码链接（NATIVE支付方式）
     * </p>
     */
    private String codeUrl;

    /**
     * H5支付链接
     * <p>
     * H5支付时返回的支付链接
     * </p>
     */
    private String h5Url;

    /**
     * 预支付交易会话标识
     * <p>
     * 微信生成的预支付会话标识，用于后续接口调用中使用
     * </p>
     */
    private String prepayId;

    /**
     * 附加数据
     * <p>
     * 商户附加数据，原样返回
     * </p>
     */
    private String attach;

    /**
     * 扩展参数
     * <p>
     * 用于存放各支付平台特有的扩展参数
     * </p>
     */
    private Map<String, Object> extraParams;

    /**
     * 创建成功的响应
     *
     * @param outTradeNo    商户订单号
     * @param transactionId 支付平台订单号
     * @param payInfo       支付信息
     * @return PayResponse
     */
    public static PayResponse success(String outTradeNo, String transactionId, Map<String, Object> payInfo) {
        return PayResponse.builder()
                .code("SUCCESS")
                .message("创建订单成功")
                .outTradeNo(outTradeNo)
                .transactionId(transactionId)
                .payInfo(payInfo)
                .build();
    }

    /**
     * 创建失败的响应
     *
     * @param message 失败原因
     * @return PayResponse
     */
    public static PayResponse fail(String message) {
        return PayResponse.builder()
                .code("FAIL")
                .message(message)
                .build();
    }
}