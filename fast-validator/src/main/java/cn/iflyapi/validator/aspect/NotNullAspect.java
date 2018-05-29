package cn.iflyapi.validator.aspect;

import cn.iflyapi.validator.annotation.NotNull;
import cn.iflyapi.validator.exception.FastValidatorException;
import cn.iflyapi.validator.util.ReflectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: qfwang
 * @date: 2018-05-21 下午6:19
 */
@Component
@Aspect
public class NotNullAspect {

    @Pointcut(value = "@annotation(cn.iflyapi.validator.annotation.NotNull)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        NotNull notNull = methodSignature.getMethod().getAnnotation(NotNull.class);
        String[] vs = notNull.value();

        for (Object o : args) {
            Class<?> c = o.getClass();
            System.out.println(o.getClass().isPrimitive());
            System.out.println(o.getClass().getName());
            System.out.println(o.getClass().getTypeName());
            for (String s : vs) {
                if (!ReflectUtils.hasFieldIncludeSuper(o.getClass(), s)) {
                    continue;
                }
                Object b = ReflectUtils.fieldValueSuper(o, s);
                if (Objects.isNull(b)) {
                    throw new FastValidatorException(s + " can not be null");
                }
            }
        }

        return joinPoint.proceed();
    }
}
