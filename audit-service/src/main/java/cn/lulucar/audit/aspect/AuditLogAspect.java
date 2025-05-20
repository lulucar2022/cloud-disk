package cn.lulucar.audit.aspect;

import cn.lulucar.audit.annotation.AuditLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志切面，拦截标注了 @AuditLog 的方法并记录日志
 * @author wxl
 */
@Aspect
@Component
public class AuditLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogAspect.class);

    @Around("@annotation(auditLog)")
    public Object logAudit(ProceedingJoinPoint joinPoint, AuditLog auditLog) throws Throwable {
        // 获取方法名和参数
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // 记录方法执行前的日志
        logger.info("开始执行方法: {}, 参数: {}, 描述: {}", methodName, args, auditLog.value());

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 记录方法执行后的日志
        logger.info("方法执行完成: {}, 返回值: {}", methodName, result);

        return result;
    }
}