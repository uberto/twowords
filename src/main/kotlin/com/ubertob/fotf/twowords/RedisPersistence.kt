package com.ubertob.fotf.twowords

import redis.clients.jedis.Jedis

object RedisPersistence {
    val tot = Words.base2k * Words.base2k
    val bigPrime = 200560490131L

    val redis = Jedis()

    //this function is to make the results more "random" looking
    fun shuffle(x: Int): Int = ((x * bigPrime) % tot).toInt()

    fun retrieveUrl(index: ShortId): FullUrl? =
        redis.get(index.key)
            ?.let(::FullUrl)

    fun saveUrl(url: FullUrl): ShortId =
        redis.incr("counter").toInt()
            .let(RedisPersistence::shuffle)
            .let(::ShortId)
            .also { redis.set(it.key, url.raw) }

}