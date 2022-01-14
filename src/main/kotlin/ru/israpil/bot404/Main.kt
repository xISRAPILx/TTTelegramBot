package ru.israpil.bot404

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.logging.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.israpil.bot404.tt.videoDownloaderHandler

fun main() {
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)
    val bot = bot {
        token = API_KEY
        logLevel = LogLevel.Error
        dispatch {
            videoDownloaderHandler(coroutineScope)
        }
    }
    bot.startPolling()
}