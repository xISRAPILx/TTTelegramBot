package ru.israpil.bot404.tt.utils

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class SimpleCookieJar : CookieJar {
    private val cookie = mutableMapOf<String, MutableList<Cookie>>()

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        cookie.getOrPut(url.host(), defaultValue = { mutableListOf() }).addAll(cookies)
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> = cookie[url.host()].orEmpty()
}

private fun <T> MutableList<T>?.orEmpty(): MutableList<T> = this ?: mutableListOf()