package cn.lulucar.common.exception;

import lombok.Getter;

/**
 * @author wxl
 * @date 2025/5/20 18:30
 * @description
 */
@Getter
public class BaseException extends RuntimeException{
    private final int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
