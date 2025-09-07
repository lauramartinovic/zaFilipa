package org.unizd.rma.curko.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApodItem(
    val date: String?,          // "2024-09-01"
    val title: String?,
    val explanation: String?,
    val url: String?,           // image or video url
    val hdurl: String?,         // hi-res image (ako postoji)
    val media_type: String?,    // "image" ili "video"
    val thumbnail_url: String?  // ako je video, thumbnail kad stavimo thumbs=true
) : Parcelable
