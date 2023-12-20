package com.example.filehandler.domains.fileuploads.models.entities

import com.example.coreweb.domains.base.entities.BaseEntityV2
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "uploaded_images", schema = "files")
class UploadedImage : BaseEntityV2() {
    @ManyToOne
    @JoinColumn(name = "image_id")
    lateinit var image: UploadProperties

    @ManyToOne
    @JoinColumn(name = "thumb_id")
    lateinit var thumb: UploadProperties
}