package com.goosvandenbekerom.shutter.saveservice.services

import com.goosvandenbekerom.shutter.saveservice.exceptions.UnsupportedImageTypeException
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.net.URLConnection


@Service
class FtpService(private val ftp: FTPClient) {
    @Value("\${ftp.host}")
    private lateinit var host: String
    @Value("\${ftp.port}")
    private var port = 21
    @Value("\${ftp.username}")
    private lateinit var username: String
    @Value("\${ftp.password}")
    private lateinit var password: String

    fun upload(id: String, image: ByteArray): Boolean {
        if (!ftp.isConnected)
            ftp.connect(host, port)

        if (!ftp.login(username, password)) {
            ftp.logout()
            return false
        }

        if (!FTPReply.isPositiveCompletion(ftp.replyCode)) {
            ftp.disconnect()
            return false
        }

        ftp.enterLocalPassiveMode()
        val ext = getImageExtensionFromBytes(image)
        val success = ftp.storeFile("$id.$ext", ByteArrayInputStream(image))
        ftp.logout()
        ftp.disconnect()
        return success
    }

    private fun getImageExtensionFromBytes(bytes: ByteArray): String {
        val contentType = URLConnection.guessContentTypeFromStream(ByteArrayInputStream(bytes))
        return when(contentType) {
            "image/jpeg" -> "jpg"
            "image/png" -> "png"
            "image/gif" -> "gif"
            "image/tiff" -> "tiff"
            else -> throw UnsupportedImageTypeException(contentType)
        }
    }
}