package com.goosvandenbekerom.shutter.sortservice.services

import com.goosvandenbekerom.shutter.sortservice.exceptions.UnsupportedImageTypeException
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.image.RenderedImage
import java.io.ByteArrayInputStream
import java.net.URLConnection
import javax.imageio.ImageIO

@Service
class SortingService {
    data class Pixel(val x: Int, val y: Int, val color: Color)

    fun sortPixels(bytes: ByteArray): Pair<RenderedImage, String> {
        val ext = getImageExtensionFromBytes(bytes)
        val image = ImageIO.read(ByteArrayInputStream(bytes))
        val pixels = mutableListOf<Pixel>()

        for (x in 0 until image.width)
            for(y in 0 until image.height)
                pixels.add(Pixel(x, y, Color(image.getRGB(x, y))))

        pixels.sortBy { it.color.rgb }

        for (p in pixels) image.setRGB(p.x, p.y, p.color.rgb)

        return Pair(image, ext)
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

