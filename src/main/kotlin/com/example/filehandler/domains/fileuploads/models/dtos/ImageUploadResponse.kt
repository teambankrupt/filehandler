package com.example.filehandler.domains.fileuploads.models.dtos

import com.fasterxml.jackson.annotation.JsonProperty

class ImageUploadResponse {
    @JsonProperty("image_url")
    lateinit var imageUrl: String

    @JsonProperty("thumb_url")
    lateinit var thumbUrl: String
}
