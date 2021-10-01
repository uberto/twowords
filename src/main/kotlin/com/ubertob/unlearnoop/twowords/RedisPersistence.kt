package com.ubertob.unlearnoop.twowords

import redis.clients.jedis.Jedis

object RedisPersistence {
    val tot = Words.base2k * Words.base2k
    val bigPrime = 200560490131L

    val redis = Jedis()

    fun shuffle(x: Int): Int = ((x * bigPrime) % tot).toInt()

    fun retrieveUrl(index: ShortId): FullUrl? =
        redis.get(index.key)
            ?.let(::FullUrl)

    fun saveUrl(url: FullUrl): ShortId =
        redis.incr("counter").toInt()
            .let(::shuffle)
            .let(::ShortId)
            .also { redis.set(it.key, url.raw) }

}