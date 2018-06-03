package com.goosvandenbekerom.shutter.saveservice.config

import org.apache.commons.net.ftp.FTPClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FtpConfig {
    @Bean
    fun ftpClient() = FTPClient()
}