package ru.israpil.bot404.tt

import okhttp3.Request

fun Request.Builder.userAgent(value: String) = header("User-Agent", value)

fun Request.Builder.referer(value: String) = header("Referer", value)