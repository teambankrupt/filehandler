package com.example.filehandler.domains.fileuploads.services

import com.example.coreweb.utils.PageAttr
import com.example.coreweb.utils.PageableParams
import com.example.filehandler.domains.fileuploads.models.entities.UploadProperties
import com.example.filehandler.domains.fileuploads.models.entities.UploadedImage
import com.example.filehandler.domains.fileuploads.repositories.FileUploadRepository
import com.example.filehandler.domains.fileuploads.repositories.ImageRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

interface FileService {
    fun searchFiles(username: String?, fileType: String?, params: PageableParams): Page<UploadProperties>

    fun saveImage(image: UploadedImage): UploadedImage
    fun searchImages(username: String?, fileType: String?, params: PageableParams): Page<UploadedImage>
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

    override fun searchImages(username: String?, fileType: String?, params: PageableParams): Page<UploadedImage> =
        this.imageRepository.search(params.query, username, PageAttr.getPageRequest(params))
}