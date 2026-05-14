package com.junoyi.platform.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 统一支付请求对象
 * <p>
 * 该类封装了创建支付订单所需的通用参数，适用于微信支付、支付宝支付等多种支付渠道。
 * 采用建造者模式，方便灵活构建支付请求。
 * </p>
 *
 * @author Fan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRequest {

    /**
     * 商户订单号
     * <p>
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
     * </p>
     */
    private String outTradeNo;

    /**
     * 商品描述
     * <p>
     * 商品简单描述，该字段须严格按照规范传递
     * </p>
     */
    private String description;

    /**
     * 订单金额
     * <p>
     * 订单总金额，单位为元，精确到小数点后两位
     * </p>
     */
    private BigDecimal amount;

    /**
     * 货币类型
     * <p>
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * </p>
     */
    @Builder.Default
    private String currency = "CNY";

    /**
     * 用户标识
     * <p>
     * 用户在商户appid下的唯一标识（微信openid、支付宝user_id等）
     * </p>
     */
    private String userId;

    /**
     * 支付场景
     * <p>
     * 支付场景类型：
     * - JSAPI：公众号支付/小程序支付
     * - APP：APP支付
     * - H5：H5支付
     * - NATIVE：扫码支付
     * </p>
     */
    private String tradeType;

    /**
     * 附加数据
     * <p>
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     * </p>
     */
    private String attach;

    /**
     * 回调通知地址
     * <p>
     * 异步接收支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
     * </p>
     */
    private String notifyUrl;

    /**
     * 订单失效时间
     * <p>
     * 订单失效时间，格式为yyyyMMddHHmmss，如20091225091010表示2009年12月25日9点10分10秒
     * </p>
     */
    private String timeExpire;

    /**
     * 商品详情
     * <p>
     * 商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传
     * </p>
     */
    private String detail;

    /**
     * 扩展参数
     * <p>
     * 用于存放各支付平台特有的扩展参数
     * </p>
     */
    private Map<String, Object> extraParams;

    /**
     * 终端IP
     * <p>
     * 用户的客户端IP，支持IPv4和IPv6两种格式的IP地址
     * </p>
     */
    private String clientIp;
}