package ru.israpil.bot404.tt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.israpil.bot404.tt.utils.SimpleCookieJar
import java.time.Duration
import java.util.regex.Pattern

private const val HOME_URL = "https://www.tiktok.com/"
private const val REQUEST_TIMEOUT = 30L
private const val SAFARI_AGENT = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) " +
        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Mobile Safari/537.36"

private const val DOWNLOAD_ADDRES_REGEX = "downloadAddr\":\\s?\"(https:.*?)\","

class VideoDownloader {
    private val downloadClient = OkHttpClient.Builder()
        .cookieJar(SimpleCookieJar())
        .callTimeout(Duration.ofSeconds(REQUEST_TIMEOUT))
        .build()

    suspend fun download(url: String) = withContext(Dispatchers.IO) {
        Pattern.compile(DOWNLOAD_ADDRES_REGEX, Pattern.MULTILINE)
            .matcher(get(url)?.string().orEmpty())
            .let {
                it.find()
                it.group(1)
            }
            ?.replace("\\u002F", "/")
            ?.let {
                get(it)?.bytes()
            }
    }

    private fun get(url: String) = downloadClient.newCall(
        Request.Builder()
            .url(url)
            .userAgent(SAFARI_AGENT)
            .referer(HOME_URL)
            .build()
    ).execute().body()
}