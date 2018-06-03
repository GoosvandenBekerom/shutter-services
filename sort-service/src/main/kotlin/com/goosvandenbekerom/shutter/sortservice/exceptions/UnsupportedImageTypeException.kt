package com.goosvandenbekerom.shutter.sortservice.exceptions

class UnsupportedImageTypeException(mediaType: String) : Exception("uploaded image has unsupported image type $mediaType")