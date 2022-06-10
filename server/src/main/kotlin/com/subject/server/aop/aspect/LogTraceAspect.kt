package com.subject.server.aop.aspect

import com.subject.server.aop.logtrace.LogTrace
import com.subject.server.aop.logtrace.TraceStatus
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.math.log

@Aspect
@Component
class LogTraceAspect(private val logTrace: LogTrace) {
    var status : TraceStatus? = null
    //로그 추적기 코틀린으로 변환 실패 : 뎁스가 낮아지는 게 제대로 출력이 안 됨
//    @Around("com.subject.server.aop.aspect.PointCuts.allAccess()")
    fun doTrace(joinPoint : ProceedingJoinPoint): Any? {
        try {
            val message = joinPoint.signature.toShortString()
            status = logTrace.begin(message)

            val result = joinPoint.proceed()

            logTrace.end(status!!)
            return result
        } catch (e : Exception){
            logTrace.exception(e, status!!)
            throw e
        }
    }
}