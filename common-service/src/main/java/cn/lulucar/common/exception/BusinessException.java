package cn.lulucar.common.exception;

/**
 * @author wxl
 * @date 2025/5/20 18:31
 * @description
 */
public class BusinessException extends BaseException{
    public BusinessException(String message) {
        super(400, message);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }
}
