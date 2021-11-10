package com.ubertob.fotf.twowords

import java.util.concurrent.atomic.AtomicInteger

object InMemoryPersistence {
    val tot = Words.base2k * Words.base2k
    val bigPrime = 200560490131L

    private val counter = AtomicInteger(1)

    private val urls = mutableMapOf<ShortId, FullUrl>()

    fun shuffle(x: Int): Int = ((x * bigPrime) % tot).toInt()

    fun retrieveUrl(index: ShortId): FullUrl? =
        urls[index]

    fun saveUrl(url: FullUrl): ShortId =
        counter.getAndIncrement()
            .let(InMemoryPersistence::shuffle)
            .let(::ShortId)
            .also { urls[it] = url }
}