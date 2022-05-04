package com.jihoonyoon.bookapitest.api

import com.jihoonyoon.bookapitest.model.BookDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookService {

    @GET("/v1/search/book.json")
    fun getBooksByQuery(
        @Header("X-Naver-Client-Id") apiID: String,
        @Header("X-Naver-Client-Secret") apiKey: String,
        @Query("query") query: String,
        @Query("start") start: Int,
    ): Call<BookDTO>
}