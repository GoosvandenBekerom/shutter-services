package com.goosvandenbekerom.shutter.saveservice.repositories

import com.goosvandenbekerom.shutter.saveservice.domain.SortedImageEntry
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : MongoRepository<SortedImageEntry, String>