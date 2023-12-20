package com.example.filehandler.domains.fileuploads.repositories

import com.example.filehandler.domains.fileuploads.models.entities.UploadProperties
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FileUploadRepository : JpaRepository<UploadProperties?, Long?> {

    @Query(
       """
        SELECT p FROM UploadProperties p 
        WHERE (:query IS NULL OR (p.fileName LIKE %:query% OR p.namespace LIKE %:query% OR p.uniqueProperty LIKE %:query%)) 
        AND (:username IS NULL OR p.createdBy=:username) 
        AND (:fileType IS NULL OR p.fileType=:fileType) 
        AND p.deleted=FALSE
       """
    )
    fun search(
        @Param("query") query: String?,
        @Param("username") username: String?,
        @Param("fileType") fileType: String?,
        pageable: Pageable
    ): Page<UploadProperties>

    @Query("SELECT p FROM UploadProperties p WHERE p.uuid=:uuid AND p.deleted=FALSE")
    fun findByUuid(@Param("uuid") uuid: String): Optional<UploadProperties>

}
