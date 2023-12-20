package com.example.filehandler.domains.fileuploads.models.dtos

import com.example.filehandler.domains.fileuploads.models.entities.UploadedImage
import com.fasterxml.jackson.annotation.JsonProperty

data class ImageUploadResponse(
    @JsonProperty("image_url")
    var imageUrl: String,

    @JsonProperty("thumb_url")
    var thumbUrl: String
)

fun UploadedImage.toResponse(baseUrl: String) = ImageUploadResponse(
    imageUrl = "$baseUrl${this.image.fileUrl}",
    thumbUrl = "$baseUrl${this.thumb.fileUrl}"
)