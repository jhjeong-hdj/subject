package com.subject.server.aop.logtrace

data class TraceStatus(
    val traceId: TraceId,
    val startTimeMillis : Long,
    val message : String
)