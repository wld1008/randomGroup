package org.wld.utils;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by lidan.wu on  2018/1/23 0023.
 */

@Component
@Aspect
public class SysLogAspectJ {

    //Controller aop
    @Pointcut("execution(public * org.wld.action.*.*(..))")
    public void checkToken(){
    }

    @Before("checkToken()")
    public void beforeCheckToken(){
        System.out.println("调用方法之前。。。。");
    }

    @AfterReturning("checkToken()")
    public void afterCheckToken(){
        System.out.println("调用方法结束之后。。。。");
    }

    //抛出异常时才调用
    @AfterThrowing("checkToken()")
    public void afterThrowing()
    {
        System.out.println("校验token出现异常了......");
    }

    //dao aop
    @Pointcut("execution(public * org.wld.*.*(..))")
    public void dao(){
    }

    @Before("dao()")
    public void beforeDao(){
        System.out.println("调用dao方法之前。。。。");
    }

    @AfterReturning("dao()")
    public void afterDao(){
        System.out.println("调用dao方法结束之后。。。。");
    }

    //抛出异常时才调用
    @AfterThrowing("dao()")
    public void afterDaoThrowing()
    {
        System.out.println("校验dao出现异常了......");
    }

}
