package com.goosvandenbekerom.shutter.saveservice.exceptions

class UnsupportedImageTypeException(mediaType: String) : Exception("uploaded image has unsupported image type $mediaType")