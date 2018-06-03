package com.goosvandenbekerom.shutter.sortservice.services

import org.springframework.stereotype.Service

@Service
class SortingService {
    fun sortPixels(bytes: ByteArray): ByteArray {
        bytes.sort()
        return bytes
    }
}