package com.example.teacherhelper.utils

import java.util.concurrent.atomic.AtomicInteger

object RandomUtil {
    private val seed = AtomicInteger()
    fun getRandomInt() = seed.getAndIncrement() + System.currentTimeMillis().toInt()
}