package com.fraud.detection.model;

import com.fraud.detection.enums.Code;
import lombok.Data;

/**
 * 返回结果
 */
@Data
public class PageResult<T> extends Result<T>{
    /**
     * 总条数
     */
    private int total;

    public static <T> PageResult<T> ok(){
        PageResult<T> result = new PageResult<>();
        result.setCode(Code.SUCCESS.getCode());
        result.setMsg(Code.SUCCESS.getMsg());
        return result;
    }

    public static <T> PageResult<T> ok(int total){
        PageResult<T> result = new PageResult<>();
        result.setCode(Code.SUCCESS.getCode());
        result.setMsg(Code.SUCCESS.getMsg());
        result.setTotal(total);
        return result;
    }

    public static <T> PageResult<T> error(Code code){
        PageResult<T> result = new PageResult<>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        return result;
    }

    public PageResult() {

    }

    public PageResult<T> setData(T data) {
        super.data = data;
        return this;
    }

    public static void main(String[] args) {
        PageResult<Boolean> result = PageResult.error(Code.SUCCESS);
    }
}
