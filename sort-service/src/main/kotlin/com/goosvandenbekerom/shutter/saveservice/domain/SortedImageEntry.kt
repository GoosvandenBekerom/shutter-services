package com.goosvandenbekerom.shutter.saveservice.domain

import org.springframework.data.annotation.Id

data class SortedImageEntry(@Id val id: String, val path: String)