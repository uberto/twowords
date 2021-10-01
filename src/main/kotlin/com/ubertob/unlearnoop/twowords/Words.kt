package com.ubertob.unlearnoop.twowords

object Words {
    private val words: List<String> = Words::class.java.getResource("/word_data.txt")
        .readText().lines()

    val base2k = 2048

    private fun word(x: Int): String = words[x % base2k]

    fun toShortUrl(id: ShortId): ShortUrl =
        ShortUrl("${word(id.number)}.${word(id.number / base2k)}")

    fun toShortId(twoWords: ShortUrl): ShortId? =
        twoWords.raw.split(".")
            .also { if (it.size != 2) return null }
            .let { ws -> words.indexOf(ws.first()) to words.indexOf(ws[1]) }
            .let { (x1, x2) -> ShortId(x1 + x2 * base2k) }
}