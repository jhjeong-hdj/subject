package com.subject.server.exception

import java.util.Optional

fun <T> Optional<T>.extract(): T {
    return this.orElseThrow()
}