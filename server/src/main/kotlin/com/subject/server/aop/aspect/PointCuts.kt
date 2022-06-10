package com.subject.server.aop.aspect

import org.aspectj.lang.annotation.Pointcut

class PointCuts {
    @Pointcut("execution(* com.subject.server..*(..))")
    private fun allSystem() {
    }

    @Pointcut("execution(* *..*Service.*(..))")
    private fun allService() {
    }

    @Pointcut("execution(* *..*Repository.*(..))")
    private fun allRepository() {
    }

    @Pointcut("execution(* *..*Controller.*(..))")
    private fun allController() {
    }

    @Pointcut("allSystem() && (allService() || allRepository() || allController())")
    fun allAccess() {
    }
}