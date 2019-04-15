package cn.iflyapi.validator.aspect;

import cn.iflyapi.validator.annotation.*;
import cn.iflyapi.validator.handler.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author flyhero
 * @date 2019-03-23 8:44 PM
 */
@Aspect
@Component
public class ValidAspect {


    private Map<Class<? extends Annotation>, IHandler> handlerMap = new HashMap<>();

    {
        handlerMap.put(NotEmpty.class, new NotEmptyHandler());
        handlerMap.put(On.class, new OnHandler());
        handlerMap.put(OnMax.class, new OnMaxHandler());
        handlerMap.put(OnMin.class, new OnMinHandler());
        handlerMap.put(Email.class, new EmailHandler());
    }

    @Pointcut(value = "@within(org.springframework.web.bind.annotation.RestController)" +
            "|| @within(org.springframework.stereotype.Controller)")
    public void pointcut() {
    }

    /**
     * @param joinPoint
     * @return
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {

            boolean present = parameters[i].isAnnotationPresent(FastValid.class);
            if (!present) {
                continue;
            }
            Object targetObj = args[i];
            Field[] fields = targetObj.getClass().getDeclaredFields();

            //遍历所有的属性
            for (Field field : fields) {

                Annotation[] annotations = field.getDeclaredAnnotations();

                if (null == annotations || annotations.length == 0) {
                    continue;
                }

                //遍历属性的所有注解去处理
                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> type = annotation.annotationType();
                    IHandler iHandler = handlerMap.get(type);
                    if (!Objects.isNull(iHandler)) {
                        iHandler.handle(annotation, field, targetObj);
                    }

                }
            }
        }

        return joinPoint.proceed();
    }

}
