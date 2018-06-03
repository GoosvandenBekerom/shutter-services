package com.goosvandenbekerom.shutter.saveservice.services

import com.goosvandenbekerom.shutter.saveservice.domain.SortedImageEntry
import com.goosvandenbekerom.shutter.saveservice.exceptions.UnsupportedImageTypeException
import com.goosvandenbekerom.shutter.saveservice.repositories.ImageRepository
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.net.URLConnection


@Service
class FtpService(private val ftp: FTPClient, private val imageRepo: ImageRepository) {
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
        val path = "$id-sorted.jpg"
        val success = ftp.storeFile(path, ByteArrayInputStream(image))
        ftp.logout()
        ftp.disconnect()

        if (success) {
            imageRepo.save(SortedImageEntry(id, path))
        }

        return success
    }
}