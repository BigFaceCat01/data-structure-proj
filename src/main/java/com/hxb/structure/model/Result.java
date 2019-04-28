package com.hxb.structure.model;

import java.io.Serializable;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-24 14:05:36
 */
public class Result<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

    private Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> build(String code, String msg, T data){
        return new Result<>(code,msg,data);
    }

    public static <T> Result<T> build(String code,  T data){
        return new Result<>(code,"",data);
    }

    public static <T> Result<T> success(T data){
        return new Result<>("0","",data);
    }

    public static <T> Result<T> success(){
        return new Result<>("0","",null);
    }

    public static <T> Result<T> fail(T data){
        return new Result<>("-1","",data);
    }

    public static <T> Result<T> fail(){
        return new Result<>("-1","",null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
