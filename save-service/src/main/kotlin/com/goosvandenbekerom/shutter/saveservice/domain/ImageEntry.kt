package com.goosvandenbekerom.shutter.saveservice.domain

import org.springframework.data.annotation.Id

data class ImageEntry(@Id val id: String, val path: String)