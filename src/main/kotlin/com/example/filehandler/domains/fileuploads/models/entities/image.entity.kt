package com.example.filehandler.domains.fileuploads.models.entities

import com.example.auth.entities.BaseEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "uploaded_images", schema = "files")
class UploadedImage : BaseEntity() {
    @ManyToOne
    @JoinColumn(name = "image_id")
    lateinit var image: UploadProperties

    @ManyToOne
    @JoinColumn(name = "thumb_id")
    lateinit var thumb: UploadProperties
}