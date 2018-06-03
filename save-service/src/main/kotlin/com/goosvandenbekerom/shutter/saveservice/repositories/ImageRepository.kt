package com.goosvandenbekerom.shutter.saveservice.repositories

import com.goosvandenbekerom.shutter.saveservice.domain.ImageEntry
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : MongoRepository<ImageEntry, String>