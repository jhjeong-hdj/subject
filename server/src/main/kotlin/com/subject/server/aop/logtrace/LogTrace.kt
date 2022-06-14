package com.subject.server.aop.logtrace

interface LogTrace {

    fun begin(message: String): TraceStatus

    fun end(status: TraceStatus)

    fun exception(e: Exception, status: TraceStatus)
}