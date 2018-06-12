package com.goosvandenbekerom.shutter.sortservice.services

import com.goosvandenbekerom.shutter.sortservice.domain.SortedImageEntry
import com.goosvandenbekerom.shutter.sortservice.repositories.ImageRepository
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPReply
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.image.RenderedImage
import javax.imageio.ImageIO


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

    fun upload(id: String, image: RenderedImage, ext: String): Boolean {
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
        ftp.setFileType(FTP.BINARY_FILE_TYPE)
        val path = "$id-sorted.$ext"
        val success = ImageIO.write(image, ext, ftp.appendFileStream(path))
        ftp.logout()
        ftp.disconnect()

        if (success) {
            imageRepo.save(SortedImageEntry(id, path))
        }

        return success
    }
}