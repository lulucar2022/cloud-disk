package cn.lulucar.common.exception;

/**
 * @author wxl
 * @date 2025/5/20 18:33
 * @description
 */
public class AuthException extends BaseException{
    public AuthException(String message) {
        super(401, message);
    }
}
