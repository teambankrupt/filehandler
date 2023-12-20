package com.example.filehandler.domains.fileuploads.services

import com.example.auth.config.security.SecurityContext
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import com.example.coreweb.utils.PageableParams
import com.example.filehandler.domains.fileuploads.models.entities.UploadProperties
import com.example.filehandler.domains.fileuploads.models.entities.UploadedImage
import com.example.filehandler.domains.fileuploads.repositories.FileUploadRepository
import com.example.filehandler.domains.fileuploads.repositories.ImageRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.time.Instant

interface FileService {
    fun searchFiles(username: String?, fileType: String?, params: PageableParams): Page<UploadProperties>

    fun saveImage(image: UploadedImage): UploadedImage
    fun searchImages(
        username: String?, fileType: String?,
        fromDate: Instant, toDate: Instant,
        params: PageableParams
    ): Page<UploadedImage>

    fun delete(id: Long, softDelete: Boolean = true)
}

@Service
class FileServiceImpl(
    private val fileUploadRepository: FileUploadRepository,
    private val imageRepository: ImageRepository
) : FileService {

    override fun searchFiles(username: String?, fileType: String?, params: PageableParams): Page<UploadProperties> =
        fileUploadRepository.search(params.query, username, fileType, PageAttr.getPageRequest(params))


    override fun saveImage(image: UploadedImage): UploadedImage =
        this.imageRepository.save(image)

    override fun searchImages(
        username: String?,
        fileType: String?,
        fromDate: Instant,
        toDate: Instant,
        params: PageableParams
    ): Page<UploadedImage> =
        this.imageRepository.search(
            query = params.query,
            username = username,
            fromDate = fromDate,
            toDate = toDate,
            pageable = PageAttr.getPageRequest(params)
        )

    override fun delete(id: Long, softDelete: Boolean) {
        val image = this.imageRepository.findById(id).orElseThrow { ExceptionUtil.notFound("Image", id) }
        if (SecurityContext.getCurrentUser().isAdmin.not() && !image.isMine) {
            throw ExceptionUtil.forbidden("You are not allowed to delete this image!")
        }
        if (softDelete) {
            image.isDeleted = true
            this.imageRepository.save(image)
        } else {
            this.imageRepository.deleteById(id)
        }
    }
}