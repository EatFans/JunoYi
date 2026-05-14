package com.junoyi.platform.enums;

/**
 * 支付状态枚举
 * <p>
 * 定义了支付订单的各种状态
 * </p>
 *
 * @author Fan
 */
public enum PayStatus {

    /**
     * 待支付
     */
    WAIT_PAY("待支付", "WAIT_PAY"),

    /**
     * 支付中
     */
    PAYING("支付中", "PAYING"),

    /**
     * 支付成功
     */
    SUCCESS("支付成功", "SUCCESS"),

    /**
     * 支付失败
     */
    FAIL("支付失败", "FAIL"),

    /**
     * 已关闭
     */
    CLOSED("已关闭", "CLOSED"),

    /**
     * 已退款
     */
    REFUNDED("已退款", "REFUNDED"),

    /**
     * 部分退款
     */
    PARTIAL_REFUND("部分退款", "PARTIAL_REFUND");

    /**
     * 状态描述
     */
    private final String label;

    /**
     * 状态编码
     */
    private final String code;

    PayStatus(String label, String code) {
        this.label = label;
        this.code = code;
    }

    /**
     * 获取状态描述
     *
     * @return 状态描述
     */
    public String getLabel() {
        return label;
    }

    /**
     * 获取状态编码
     *
     * @return 状态编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 根据编码获取支付状态
     *
     * @param code 状态编码
     * @return 支付状态枚举，如果不存在则返回 null
     */
    public static PayStatus fromCode(String code) {
        for (PayStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

