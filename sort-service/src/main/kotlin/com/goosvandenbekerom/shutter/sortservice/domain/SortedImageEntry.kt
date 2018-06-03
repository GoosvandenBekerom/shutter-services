package com.goosvandenbekerom.shutter.sortservice.domain

import org.springframework.data.annotation.Id

data class SortedImageEntry(@Id val id: String, val path: String)