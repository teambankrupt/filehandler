package com.example.filehandler.domains.fileuploads.controllers

import com.example.auth.config.security.SecurityContext
import com.example.common.exceptions.invalid.ImageInvalidException
import com.example.coreweb.domains.base.models.enums.SortByFields
import com.example.coreweb.utils.PageableParams
import com.example.filehandler.domains.fileuploads.models.dtos.*
import com.example.filehandler.domains.fileuploads.models.entities.UploadedImage
import com.example.filehandler.domains.fileuploads.services.FileService
import com.example.filehandler.domains.fileuploads.services.FileUploadService
import com.example.filehandler.routing.Route
import com.example.filehandler.utils.ImageValidator
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Api(tags = ["Uploads"], description = "Handle File Uploads including images")
@RestController
class ImageControllerV2 @Autowired constructor(
    private val uploadService: FileUploadService,
    private val fileService: FileService
) {
    @Value("\${app.base-url-image}")
    lateinit var baseUrlImages: String

    // UPLOAD IMAGES
    @PostMapping(Route.V1.UPLOAD_IMAGE)
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
        @RequestParam(value = "namespace") namespace: String
    ): ResponseEntity<ImageUploadResponse> {
        var image = upload(file, namespace, SecurityContext.getLoggedInUsername())
        image = this.fileService.saveImage(image)
        return ResponseEntity.ok(image.toResponse(this.baseUrlImages))
    }

    // UPLOAD IMAGES
    @PostMapping(Route.V1.UPLOAD_IMAGE_BULK)
    fun uploadImages(
        @RequestParam("files") files: Array<MultipartFile>,
        @RequestParam(value = "namespace") namespace: String
    ): ResponseEntity<*> {
        if (files.isEmpty()) return ResponseEntity.badRequest().body("At least one image is expected!")

        val images = files.map {
            val image = upload(it, namespace, SecurityContext.getLoggedInUsername())
            this.fileService.saveImage(image)
        }

        return ResponseEntity.ok(images.map { it.toResponse(this.baseUrlImages) })
    }

    @GetMapping(Route.V1.MY_FILES)
    fun search(
        @RequestParam("type") fileType: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("q", required = false) query: String?,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction,
    ): ResponseEntity<Page<ImageDto>> {
        val files = this.fileService.searchImages(
            SecurityContext.getLoggedInUsername(),
            fileType,
            PageableParams.of(query, page, size, sortBy, direction)
        )
        return ResponseEntity.ok(files.map { it.toDto(this.baseUrlImages) })
    }

    private fun upload(file: MultipartFile, namespace: String, username: String): UploadedImage {
        if (!ImageValidator.isImageValid(file)) throw ImageInvalidException("Invalid Image")

        val image = uploadService.uploadImage(file, namespace, username, 1200)
        val thumb = uploadService.uploadImage(file, namespace, username + File.separator + "thumbs", 600)

        return UploadedImage().apply {
            this.image = image
            this.thumb = thumb
        }
    }
}
