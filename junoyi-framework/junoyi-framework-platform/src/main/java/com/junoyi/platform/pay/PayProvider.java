package com.junoyi.platform.pay;

import com.junoyi.platform.domain.PayRequest;
import com.junoyi.platform.domain.PayResponse;
import com.junoyi.platform.enums.Platform;

/**
 * 支付提供者接口
 * <p>
 * 该接口定义了第三方支付平台的基本契约，用于统一管理和集成不同的支付渠道，
 * 如微信支付、支付宝支付等平台的支付功能。
 * </p>
 *
 * @author Fan
 */
public interface PayProvider {

    /**
     * 获取支付平台类型
     *
     * @return 支付平台枚举值，如WECHAT、WEWORK等
     */
    Platform getPlatform();

    /**
     * 创建支付订单
     * <p>
     * 根据支付请求参数创建新的支付订单，并返回支付响应信息。
     * </p>
     *
     * @param request 支付请求对象，包含订单金额、商品描述、用户信息等支付所需参数
     * @return 支付响应对象，包含订单号、支付链接、二维码等支付结果信息
     */
    PayResponse createOrder(PayRequest request);

}
