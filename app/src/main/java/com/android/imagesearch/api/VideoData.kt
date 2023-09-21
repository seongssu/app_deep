package com.android.imagesearch.api

data class VideoData(
    val meta : VideoMeta,
    val documents: ArrayList<VideoDocument>
)

data class VideoMeta(
    val total_count : Int,
    val pageable_count : Int,
    val is_end : Boolean
)

data class VideoDocument(
    val url : String,
    val title : String,
    val datetime: String,
    val play_time : Int,
    val thumbnail : String,
    val author : String
)