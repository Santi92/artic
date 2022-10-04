package com.example.artsproject.data.dto

data class ResponseArt(
    val pagination: Pagination,
    val data: List<Art>,
)

data class Pagination(
    val total: Int
)

data class Art(
    val id:Int,
    val apiLink: String,
    val thumbnail: Thumbnail,
    val image_id: String,
    val medium_display: String?,
){
    fun getUrlImage() = "https://www.artic.edu/iiif/2/$image_id/full/200,/0/default.jpg"
}

data class Thumbnail(
    val alt_text: String?,
    val lqip: String,
)

