package com.fraud.detection.model;


import lombok.Data;


import com.fraud.detection.enums.Code;

/**
 * 返回结果
 */
@Data
public class Result<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回的具体内容
     */
    protected T data;

    public static <T> Result<T> ok(){
        Result<T> result = new Result<>();
        result.setCode(Code.SUCCESS.getCode());
        result.setMsg(Code.SUCCESS.getMsg());
        return result;
    }
    public static <T> Result<T> error(Code code){
        Result<T> result = new Result<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        return result;
    }

    public Result() {

    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static void main(String[] args) {
        Result<Boolean> result = Result.error(Code.SUCCESS);
    }
}
