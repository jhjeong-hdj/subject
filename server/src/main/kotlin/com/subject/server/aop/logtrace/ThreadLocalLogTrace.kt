package com.subject.server.aop.logtrace

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ThreadLocalLogTrace : LogTrace {
    private val traceHolder = ThreadLocal<TraceId>()

    override fun begin(message: String): TraceStatus {
        syncTraceId()
        val traceId = traceHolder.get()
        val startTimeMillis = System.currentTimeMillis()
        log.info("[{}] {}{}", traceId.id, addSpace(START_PREFIX, traceId.level), message)
        return TraceStatus(traceId, startTimeMillis, message)
    }

    private fun syncTraceId() {
        val traceId = traceHolder.get()
        traceId?.let {
            traceHolder.set(it.createNextId())
        } ?: traceHolder.set(TraceId())
    }

    override fun end(status: TraceStatus) {
        complete(status, null)
    }

    override fun exception(e: Exception, status: TraceStatus) {
        complete(status, e)
    }

    private fun complete(status: TraceStatus, e: Exception?) {
        val stopTimeMillis = System.currentTimeMillis()
        val resultTimeMillis = stopTimeMillis - status.startTimeMillis
        val traceId = status.traceId
        e?.let {
            log.info(
                "[{}] {}{} time={}ms ex={}",
                traceId.id,
                addSpace(EX_PREFIX, traceId.level),
                status.message,
                resultTimeMillis,
                e.toString()
            )
        } ?: log.info(
            "[{}] {}{} time={}ms",
            traceId.id,
            addSpace(COMPLETE_PREFIX, traceId.level),
            status.message,
            resultTimeMillis
        )

        releaseTraceId()
    }

    private fun releaseTraceId() {
        val traceId = traceHolder.get()
        when {
            traceId.isFirstLevel() -> traceHolder.remove()
            else -> traceHolder.set(traceId.createPreviousId())
        }
    }

    private fun addSpace(prefix: String, level: Int): Any {
        val stringBuilder = StringBuilder()
        for (i in 0..level) {
            when (i) {
                (level - 1) -> stringBuilder.append("|$prefix")
                else -> stringBuilder.append("|   ")
            }
        }

        return stringBuilder.toString()
    }

    companion object {
        const val START_PREFIX = "-->"
        const val COMPLETE_PREFIX = "<--"
        const val EX_PREFIX = "<X-"

        private val log: Logger = LoggerFactory.getLogger(ThreadLocalLogTrace::class.java)
    }
}