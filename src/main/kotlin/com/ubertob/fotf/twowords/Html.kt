package com.ubertob.fotf.twowords

data class HtmlPage(val raw: String)

fun htmlHello(): HtmlPage =
    HtmlPage(
        """
        <html><body>
        
        <h1>Hello to TwoWords</h1>
        
        <p>To shorten an url try: /generate?url=https://www.xxx.com</p>
        </body></html>   
        """.trimIndent()
    )

fun htmlShortenedPage(short: ShortUrl): HtmlPage =
    HtmlPage(
        """<html><body><h1>
        The shorten Url is ${"/x/${short.raw}".toAnchor()}
        </h1></body></html>
        """.trimIndent()
    )


fun htmlFullUrlPage(fullUrl: FullUrl): HtmlPage = HtmlPage(
    """<html><body><h1>
        Full Url is ${fullUrl.raw.toAnchor()}
        </h1></body></html>
        """.trimIndent()
)


private fun String?.toAnchor(): String? =
    if (this != null) """<a href="$this">$this</a>""" else "none!"

