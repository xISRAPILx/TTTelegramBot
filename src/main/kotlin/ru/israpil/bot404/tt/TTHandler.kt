package ru.israpil.bot404.tt

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.TelegramFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URL

fun Dispatcher.videoDownloaderHandler(
    coroutineScope: CoroutineScope
) {
    val videoDownloader = VideoDownloader()

    text {
        val url = text.toURL()
        if (url == null || url.host?.contains("tiktok", ignoreCase = true) == false) return@text

        coroutineScope.launch {
            try {
                val video = videoDownloader.download(message.text.orEmpty())
                if (video != null) {
                    bot.sendVideo(
                        chatId = ChatId.fromId(message.chat.id),
                        video = TelegramFile.ByByteArray(video, url.path),
                        replyToMessageId = message.messageId
                    )
                } else {
                    bot.sendMessage(
                        chatId = ChatId.fromId(message.chat.id),
                        text = "Не удалось скачать видео :(",
                        replyToMessageId = message.messageId
                    )
                }
            } catch (ex: Exception) {
                bot.sendMessage(
                    chatId = ChatId.fromId(message.chat.id),
                    text = ex.message.orEmpty(),
                    replyToMessageId = message.messageId
                )
                println(ex.message)
                ex.printStackTrace()
            }
        }
    }
}

private fun String.toURL(): URL? = try {
    URL(this)
} catch (_: Exception) {
    null
}