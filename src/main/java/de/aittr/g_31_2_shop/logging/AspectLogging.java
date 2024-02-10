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

   /* @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getAllActiveProducts(..))")
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
    }*/

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

   /* @Pointcut("execution(* de.aittr.g_31_2_shop.services.jpa.JpaProductService.getActiveProductById(int))")
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
    }*/

    @Pointcut("within(de.aittr.g_31_2_shop.services.jpa.JpaProductService)")
    public void allMethodsInJpaProductService() {}

    @Before("allMethodsInJpaProductService()")
    public void beforeMethodsInJpaProductService(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            logger.info("The method {} was called with parameter {}", joinPoint.getSignature().getName(),
                    args[0]);
        } else {
            logger.info("The method {} was called", joinPoint.getSignature().getName());
        }
    }

    @After("allMethodsInJpaProductService()")
    public void afterMethodsInJpaProductService(JoinPoint joinPoint) {
        logger.info("The method {} has finished its work", joinPoint.getSignature().getName());
    }

    @Pointcut("within(de.aittr.g_31_2_shop.services..*)")
    public void allServicesMethods() {}

    @Before("allServicesMethods()")
    public void beforeServiceMethodExecution(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            logger.info("In the class {} the method {} was called with parameter {}",
                    joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                    args[0]);
        } else {
            logger.info("In the class {} the method {} was called", joinPoint.getTarget().getClass().getName(),
                    joinPoint.getSignature().getName());

        }
    }

    @AfterReturning(pointcut = "allServicesMethods()", returning = "result")
    public void afterServiceMethodExecution(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        if (args != null && args.length > 0) {
            logger.info("In the class {} the method {} was called with parameter {} and has successfully returned result: {}",
                    joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(),
                    args[0], result);
        } else {
            logger.info("In the class {} the method {} was called and has successfully returned result: {}", joinPoint.getTarget().getClass().getName(),
                    joinPoint.getSignature().getName(), result);

        }
    }

    @AfterThrowing(pointcut = "allServicesMethods()", throwing = "e")
    public void afterServiceMethodError(JoinPoint joinPoint, Throwable e) {
        Object[] args = joinPoint.getArgs();

        if (args !=null && args.length > 0) {
            logger.error("The method {} of the class {} has invoked with the argument {} and has thrown an error: {}",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(), args[0],
                    e.getMessage());
        } else {
            logger.error("The method {} of the class {} has invoked and has thrown an error: {}",
                    joinPoint.getSignature().getName(), joinPoint.getTarget().getClass().getName(),
                    e.getMessage());
        }
    }
}
