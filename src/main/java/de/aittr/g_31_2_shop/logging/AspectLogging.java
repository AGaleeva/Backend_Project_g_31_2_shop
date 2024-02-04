package de.aittr.g_31_2_shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getAllActiveProducts(..))")
    public void getProducts() {}

    @Before("getProducts()")
    public void beforeGetProducts() {
        logger.info("The method getProducts() was called");
    }

    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.restoreById(int))")
    public void restoreProducts() {}

    @After("restoreProducts()")
    public void afterRestoreProduct(JoinPoint joinPoint) {
//        joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("The method restoreById by id {} was called.", args[0]);
    }

//    Как написать логгер для метода с неизвестным количеством аргументов
    public void testAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder("A method by parameters was called ");
        for (Object arg : args) {
            builder.append(arg).append(", ");
        }
        builder.setLength(builder.length() - 2); // обрезаем в конце лишнюю запятую и пробел
        builder.append(".");
        logger.info(builder.toString());
    }
}
