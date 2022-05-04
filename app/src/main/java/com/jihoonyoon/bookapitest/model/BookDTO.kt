package com.jihoonyoon.bookapitest.model

import com.google.gson.annotations.SerializedName

data class BookDTO(
    @SerializedName("total") val total : Int,
    @SerializedName("start") val start : Int,
    @SerializedName("items") val items: List<Book>
)
