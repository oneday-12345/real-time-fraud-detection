package com.fraud.detection.enums;

/**
 * 状态码
 */
public enum Code {

    SUCCESS(0, "Success."),
    SYS_ERROR(-1, "System error."),

    VALIDATOR_DENY(-1000, "validator deny."),
    VALIDATOR_NEED_CHECK(-1001, "validator need check."),
    VALIDATOR_NEED_PASS(-1002, "validator pass."),

    VALIDATOR_FUNCTION_ERROR(-2000, "validator function error."),
    VALIDATOR_TYPE_ERROR(-2001, "validator type error."),

    FSM_STATE_ERROR(-3000, "FSM state name error."),
    FSM_STATE_INFINITE_ERROR(-3001, "FSM state infinite error."),

    LAST_ERROR(-999999999, "Last error.");

    private int code;
    private String msg;

    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
