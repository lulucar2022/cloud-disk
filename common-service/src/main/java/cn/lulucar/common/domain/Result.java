package cn.lulucar.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author wxl
 * @date 2025/5/10 16:30
 * @description 统一返回类型 Result，用于封装所有接口的返回值。
 * 包含状态码、消息和数据。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // 状态码
    private int code;
    // 消息
    private String message;
    // 数据
    private T data;
    // 时间戳
    private long timestamp;

    // 成功返回（带数据）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data, System.currentTimeMillis());
    }

    // 成功返回（不带数据）
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null, System.currentTimeMillis());
    }

    // 失败返回（自定义错误）
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null, System.currentTimeMillis());
    }

    // 失败返回（默认错误）
    public static <T> Result<T> error() {
        return new Result<>(500, "操作失败", null, System.currentTimeMillis());
    }
}