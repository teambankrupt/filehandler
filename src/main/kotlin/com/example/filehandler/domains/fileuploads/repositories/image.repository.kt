package com.example.filehandler.domains.fileuploads.repositories

import com.example.filehandler.domains.fileuploads.models.entities.UploadedImage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ImageRepository : JpaRepository<UploadedImage, Long> {
    @Query(
        """
        SELECT i FROM UploadedImage i 
        WHERE (:query IS NULL OR (i.image.fileName LIKE %:query% OR i.image.namespace LIKE %:query% OR i.image.uniqueProperty LIKE %:query%)) 
        AND (:username IS NULL OR i.createdBy=:username)
        AND (i.createdAt BETWEEN :fromDate AND :toDate)
        AND i.deleted=FALSE
       """
    )
    fun search(
        @Param("query") query: String?,
        @Param("username") username: String?,
        @Param("fromDate") fromDate: Instant,
        @Param("toDate") toDate: Instant,
        pageable: Pageable
    ): Page<UploadedImage>
}