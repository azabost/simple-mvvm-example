package com.azabost.simplemvvm.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

val Any.logger: Logger get() = javaClass.logger
val KClass<*>.logger: Logger get() = java.logger
val Class<*>.logger: Logger get() = LoggerFactory.getLogger(this)