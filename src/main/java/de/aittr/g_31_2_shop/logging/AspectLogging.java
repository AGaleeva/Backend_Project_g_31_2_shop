package de.aittr.g_31_2_shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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
        logger.info("The method getProducts() was called.");
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
        // (String name, int id) -> ["petya", 6] Вызван метод с параметрами, petya, 6
        // (int id) -> [7] Вызван метод с параметрами 7
        // () -> []
        // (double price, String name, Cat cat, Product product)
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder("A method by parameters was called.");
        for (Object arg : args) {
            builder.append(arg).append(", ");
        }
        builder.setLength(builder.length() - 2); // обрезаем в конце лишнюю запятую и пробел
        builder.append(".");
        logger.info(builder.toString());
    }

    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getActiveProductById(int))")
    public void getActiveProductById() {}

    @AfterReturning(
            pointcut = "getActiveProductById()",
            returning = "result"
    )
    public void afterReturningProduct(JoinPoint joinPoint, Object result) {
        Object id = joinPoint.getArgs()[0];
        logger.info("The method getActiveProductById(int) was called with parameter {} and has returned a result " +
                "successfully: {}", id, result);
    }

    @AfterThrowing(
            pointcut = "getActiveProductById()",
            throwing = "e"
    )
    public void throwingExceptionWhileReturningProduct(JoinPoint joinPoint, Exception e) {
        Object id = joinPoint.getArgs()[0];
        logger.warn("The method getActiveProductById(int) has invoked with the parameter {} and has thrown an error: " +
                        "{}.", id, e.getMessage());
    }

    @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getActiveProductCount(..))")
    public void getActiveProductCount() {}

    @Around("getActiveProductCount()")
    public Object aroundGettingProductCount(ProceedingJoinPoint joinPoint) {
        logger.info("The method getActiveProductCount() has been called.");
        long start = System.currentTimeMillis();

        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        long time = System.currentTimeMillis() - start;
        logger.info("The method getActiveProductCount() executed in {} milliseconds with the result {}.", time, result);

//        return result;
        logger.info("We replace the actual result with our own value - 500.");
        return 500;
    }
}
