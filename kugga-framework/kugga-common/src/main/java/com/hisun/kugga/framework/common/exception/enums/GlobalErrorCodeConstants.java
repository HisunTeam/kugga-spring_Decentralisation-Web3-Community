package com.hisun.kugga.framework.common.exception.enums;

import com.hisun.kugga.framework.common.exception.ErrorCode;

/**
 * 全局错误码枚举
 * 0-999 系统异常编码保留
 * <p>
 * 一般情况下，使用 HTTP 响应状态码 https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status
 * 虽然说，HTTP 响应状态码作为业务使用表达能力偏弱，但是使用在系统层面还是非常不错的
 * 比较特殊的是，因为之前一直使用 0 作为成功，就不使用 200 啦。
 *
 * @author 芋道源码
 */
public interface GlobalErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(0, "Success");

    // ========== 客户端错误段 ==========

    ErrorCode BAD_REQUEST = new ErrorCode(400, "Incorrect request parameters");
    ErrorCode UNAUTHORIZED = new ErrorCode(401, "unauthorized");
    ErrorCode FORBIDDEN = new ErrorCode(403, "forbidden");
    ErrorCode NOT_FOUND = new ErrorCode(404, "not found");
    ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "method not allowed");
    ErrorCode LOCKED = new ErrorCode(423, "locked try again later"); // 并发请求，不允许
    ErrorCode TOO_MANY_REQUESTS = new ErrorCode(429, "too many requests");
    ErrorCode REQUEST_TIMEOUT = new ErrorCode(304, "async request timeout");

    // ========== 服务端错误段 ==========

    ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "Sorry, the system is busy");

    // ========== 自定义错误段 ==========
    ErrorCode REPEATED_REQUESTS = new ErrorCode(900, "repeated requests"); // 重复请求
    ErrorCode DEMO_DENY = new ErrorCode(901, "demo deny");

    ErrorCode UNKNOWN = new ErrorCode(999, "unknown error");

    /**
     * 是否为服务端错误，参考 HTTP 5XX 错误码段
     *
     * @param code 错误码
     * @return 是否
     */
    static boolean isServerErrorCode(Integer code) {
        return code != null
                && code >= INTERNAL_SERVER_ERROR.getCode() && code <= INTERNAL_SERVER_ERROR.getCode() + 99;
    }

}
