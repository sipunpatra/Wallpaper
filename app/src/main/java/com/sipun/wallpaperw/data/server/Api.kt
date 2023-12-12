package com.sipun.wallpaperw.data.server

import com.sipun.wallpaperw.data.model.UnSplashPhoto
import com.sipun.wallpaperw.utils.TOKEN
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String = TOKEN,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 5
    ): List<UnSplashPhoto>
}