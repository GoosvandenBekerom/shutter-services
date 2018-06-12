package com.goosvandenbekerom.shutter.sortservice.services

import com.goosvandenbekerom.shutter.sortservice.exceptions.UnsupportedImageTypeException
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.net.URLConnection
import javax.imageio.ImageIO

@Service
class SortingService {
    data class Pixel(val x: Int, val y: Int, val rgb: Int)

    fun sortPixels(bytes: ByteArray): Pair<BufferedImage, String> {
        val ext = getImageExtensionFromBytes(bytes)
        val img = ImageIO.read(ByteArrayInputStream(bytes))

        val pixels = mutableListOf<Pixel>()

        for (x in 0 until img.width)
            for (y in 0 until img.height)
                pixels.add(Pixel(x, y, img.getRGB(x,y)))

        val sorted = pixels.sortedBy { it.rgb }

        var counter = 0
        for (x in 0 until img.width)
            for (y in 0 until img.height)
                img.setRGB(x, y, sorted[counter++].rgb)

        return Pair(img, ext)
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

