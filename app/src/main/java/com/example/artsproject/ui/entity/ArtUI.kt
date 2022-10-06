package com.example.artsproject.ui.entity

import android.os.Parcelable
import com.example.artsproject.data.dto.Art
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtUI(
    val id: Int,
    val text:String,
    val imgUrl:String,
    val provenance: String,
    val artist: String?,
    val timestamp:String?,
    val listImage: List<String>,
):Parcelable

fun Art.toEntityUI():ArtUI{
    return ArtUI(
        text = medium_display ?: "",
        imgUrl = getUrlImage(),
        id = id,
        provenance = provenance_text ?: "",
        artist = artist_title,
        timestamp = timestamp,
        listImage = getListImage(),
    )
}