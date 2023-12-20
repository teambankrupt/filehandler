package com.example.filehandler.domains.fileuploads.models.dtos

import com.example.filehandler.domains.fileuploads.models.entities.UploadedImage
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class ImageDto(
    @JsonProperty("created_at")
    val createdAt: Instant,

    @JsonProperty("updated_at")
    val updatedAt: Instant,

    @JsonProperty("uploaded_by")
    val uploadedBy: String,

    @JsonProperty("image_url")
    val imageUrl: String,

    @JsonProperty("thumb_url")
    val thumbUrl: String
)

fun UploadedImage.toDto(baseUrl: String) = ImageDto(
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    uploadedBy = this.createdBy,
    imageUrl = "$baseUrl/${this.image.fileUrl}",
    thumbUrl = "$baseUrl/${this.thumb.fileUrl}"
)