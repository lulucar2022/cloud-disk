package cn.lulucar.common.advice;

import cn.lulucar.common.domain.Result;
import cn.lulucar.common.exception.AuthException;
import cn.lulucar.common.exception.BusinessException;
import cn.lulucar.common.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

/**
 * @author wxl
 * @date 2025/5/10 17:00
 * @description 全局异常处理器，用于统一处理异常并返回标准格式的 Result<T> 响应。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理认证异常
    @ExceptionHandler(AuthException.class)
    public Result<?> handleAuthException(AuthException e) {
        log.error("认证失败: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理权限异常
    @ExceptionHandler(ForbiddenException.class)
    public Result<?> handleForbiddenException(ForbiddenException e) {
        log.error("权限不足: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理参数校验异常（@RequestBody）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("参数校验失败: {}", errorMsg);
        return Result.error(400, errorMsg);
    }

    // 处理参数校验异常（@RequestParam/@PathVariable）
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMsg = e.getConstraintViolations().iterator().next().getMessage();
        log.error("参数校验失败: {}", errorMsg);
        return Result.error(400, errorMsg);
    }

    // 处理参数类型不匹配异常
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数类型不匹配: {}", e.getMessage());
        return Result.error(400, "参数类型不匹配");
    }

    // 处理请求体解析异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求体解析失败: {}", e.getMessage());
        return Result.error(400, "请求体格式错误");
    }

    // 处理未知异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统内部错误", e);
        return Result.error(500, "系统内部错误，请稍后重试");
    }
}