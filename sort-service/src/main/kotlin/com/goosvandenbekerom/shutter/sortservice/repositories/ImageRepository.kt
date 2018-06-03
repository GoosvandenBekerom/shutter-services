package com.goosvandenbekerom.shutter.sortservice.repositories

import com.goosvandenbekerom.shutter.sortservice.domain.SortedImageEntry
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : MongoRepository<SortedImageEntry, String>