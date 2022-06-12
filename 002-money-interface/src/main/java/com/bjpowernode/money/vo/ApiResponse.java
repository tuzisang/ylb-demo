package com.bjpowernode.money.vo;

/**
 * @author tzsang
 * @create 2022-06-11 21:03
 */
public class ApiResponse {

    private Integer code;
    private String message;
    private Object data;

    public static ApiResponse success(String message){
        return new ApiResponse(200, message,null);
    }
    public static ApiResponse success(String message,Object data){
        return new ApiResponse(200, message,data);
    }
    public static ApiResponse error(String message){
        return new ApiResponse(500, message,null);
    }

    public ApiResponse() {
    }

    public ApiResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
