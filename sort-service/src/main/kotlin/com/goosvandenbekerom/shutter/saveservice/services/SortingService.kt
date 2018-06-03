package com.goosvandenbekerom.shutter.saveservice.services

import org.springframework.stereotype.Service

@Service
class SortingService {
    fun sortPixels(bytes: ByteArray): ByteArray {
        bytes.sort()
        return bytes
    }
}