package cn.lulucar.common.exception;

/**
 * @author wxl
 * @date 2025/5/20 18:33
 * @description
 */
public class ForbiddenException extends BaseException{
    public ForbiddenException(String message) {
        super(403, message);
    }
}
